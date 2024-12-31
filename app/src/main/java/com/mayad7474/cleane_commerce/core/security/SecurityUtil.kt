package com.mayad7474.cleane_commerce.core.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.BLOCK_MODE_CBC
import android.security.keystore.KeyProperties.ENCRYPTION_PADDING_PKCS7
import android.security.keystore.KeyProperties.KEY_ALGORITHM_AES
import android.security.keystore.KeyProperties.PURPOSE_DECRYPT
import android.security.keystore.KeyProperties.PURPOSE_ENCRYPT
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject

class SecurityUtil @Inject constructor() {

    private val provider = "AndroidKeyStore"
    private val charset by lazy { charset("UTF-8") }
    private val keyStore by lazy { KeyStore.getInstance(provider).apply { load(null) } }

    fun encryptData(
        keyAlias: String, text: String, useInitializationVector: Boolean = true
    ): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateKey(keyAlias))

        var result = ""
        if (useInitializationVector) {
            val iv = cipher.iv
            val ivString = Base64.encodeToString(iv, Base64.DEFAULT)
            result = ivString + IV_SEPARATOR
        }

        try {
            val bytes = cipher.doFinal(text.toByteArray(charset))
            result += Base64.encodeToString(bytes, Base64.DEFAULT)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            throw e
        }
        return result
    }

    fun decryptData(
        keyAlias: String, text: String, useInitializationVector: Boolean = true
    ): String {
        val encodedString: String
        val cipher = Cipher.getInstance(TRANSFORMATION)

        if (useInitializationVector) {
            val split = text.split(IV_SEPARATOR.toRegex())
            if (split.size != 2) throw IllegalArgumentException("Passed data is incorrect. There was no IV specified with it.")

            val ivString = split[0]
            encodedString = split[1]
            val ivSpec = IvParameterSpec(Base64.decode(ivString, Base64.DEFAULT))
            cipher.init(Cipher.DECRYPT_MODE, getOrCreateKey(keyAlias), ivSpec)
        } else {
            encodedString = text
            cipher.init(Cipher.DECRYPT_MODE, getOrCreateKey(keyAlias))
        }

        val encryptedData = Base64.decode(encodedString, Base64.DEFAULT)
        val decodedData = cipher.doFinal(encryptedData)
        return decodedData.toString(charset)
    }

    private fun getOrCreateKey(keyAlias: String): SecretKey =
        (keyStore.getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry)?.secretKey
            ?: generateKey(keyAlias)

    private fun generateKey(keyAlias: String): SecretKey =
        KeyGenerator.getInstance(ALGORITHM, provider)
            .apply { init(getKeyGen(keyAlias)) }
            .generateKey()

    private fun getKeyGen(keyAlias: String) = KeyGenParameterSpec.Builder(
        keyAlias, PURPOSE_ENCRYPT or PURPOSE_DECRYPT
    ).apply {
        setBlockModes(BLOCK_MODE)
        setEncryptionPaddings(PADDING)
        setUserAuthenticationRequired(false)
//        setRandomizedEncryptionRequired(true)
    }.build()

    companion object {
        private const val IV_SEPARATOR: String = "]"
        private const val ALGORITHM = KEY_ALGORITHM_AES
        private const val BLOCK_MODE = BLOCK_MODE_CBC
        private const val PADDING = ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }
}