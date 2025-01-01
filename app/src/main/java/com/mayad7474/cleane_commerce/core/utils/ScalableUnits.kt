package com.mayad7474.cleane_commerce.core.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mayad7474.cleane_commerce.core.extensions.logi

//// Constants for ranges and prefixes
//private const val SDP_MIN = 1
//private const val SDP_MAX = 600
//private const val NEGATIVE_SDP_MIN = -60
//private const val NEGATIVE_SDP_MAX = -1
//private const val SDP_PREFIX = "_%dsdp"
//private const val NEGATIVE_SDP_PREFIX = "_minus%dsdp"
//
//// Cache for resource IDs
//private val cachedResourceIds = mutableMapOf<String, Int>()
//
//val Int.sdp: Dp
//    @Composable
//    get() = this.sdpGet()
//
//val Int.ssp: TextUnit
//    @Composable get() = this.textSdp(density = LocalDensity.current)
//
//@Composable
//private fun Int.textSdp(density: Density): TextUnit = with(density) {
//    this@textSdp.sdp.toSp()
//}
//
//@Composable
//private fun Int.sdpGet(): Dp {
//    val id = when (this) {
//        in SDP_MIN..SDP_MAX -> SDP_PREFIX.format(this)
//        in NEGATIVE_SDP_MIN..NEGATIVE_SDP_MAX -> NEGATIVE_SDP_PREFIX.format(this)
//        else -> return this.dp
//    }
//
//    val resourceField = getFieldId(id, LocalContext.current)
//    return if (resourceField != 0) dimensionResource(id = resourceField) else this.dp
//}
//
//private fun getFieldId(id: String, context: Context): Int {
//    return cachedResourceIds.getOrPut(id) {
//        val resourceId = context.resources.getIdentifier(id, "dimen", context.packageName)
//        if (resourceId == 0) "DimensionHelper - Resource not found for ID: $id".logi()
//        resourceId
//    }
//}

enum class PaddingSize(val value: Int) {
    VerySmall(4),
    Small(8),
    Medium(16),
    Large(24),
    ExtraLarge(32),
    Huge(42);

    @Composable
    fun toDp(): Dp {
        return with(LocalDensity.current) { value.dp }
    }
}


enum class TextSize(val value: Int) {
    Small(12),
    Medium(16),
    Large(20),
    ExtraLarge(26);

    @Composable
    fun toSp(): TextUnit {
        return with(LocalDensity.current) { value.sp }
    }
}
