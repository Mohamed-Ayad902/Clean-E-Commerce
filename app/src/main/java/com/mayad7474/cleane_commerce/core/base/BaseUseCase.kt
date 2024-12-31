package com.mayad7474.cleane_commerce.core.base

import com.mayad7474.cleane_commerce.core.exceptions.toAppError
import com.mayad7474.cleane_commerce.core.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

abstract class BaseUseCase<in Params, Result> {

    // Abstract function to define the core execution logic
    protected abstract suspend fun execute(params: Params): Result

    // Public function to be used by ViewModels or other layers
    fun invoke(params: Params): Flow<Resource<Result>> = safeCallAsFlow {
        execute(params) // Call the abstract execute function
    }

    // Safe execution with error handling
    private fun safeCallAsFlow(action: suspend () -> Result): Flow<Resource<Result>> = flow {
        emit(Resource.Loading(true)) // Emit loading state
        try {
            val result = action() // Execute the action
            emit(Resource.Success(result)) // Emit success dynamically
        } catch (e: Throwable) {
            emit(Resource.Error(e.toAppError())) // Emit error dynamically
        } finally {
            emit(Resource.Loading(false)) // Emit loading complete
        }
    }.onEach { delay(50) } // Ensure UI processing
        .distinctUntilChanged() // Prevent duplicate emissions
}