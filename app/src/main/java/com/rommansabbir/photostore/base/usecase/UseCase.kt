package com.rommansabbir.photostore.base.usecase

import com.rommansabbir.photostore.base.either.Either
import com.rommansabbir.photostore.base.failure.Failure
import kotlinx.coroutines.*

abstract class UseCase<out Type, in Params> where Type : Any {
    private val mainJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + mainJob)

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) =
        uiScope.launch { onResult(withContext(Dispatchers.IO) { run(params) }) }

    /*Use clients CoroutineScope instead of UseCase one, so that any operation
    that is ongoing under the CoroutineScope will be cancelled according to
    the lifecycle state of Client*/
    operator fun invoke(
        scope: CoroutineScope,
        params: Params,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        scope.launch { onResult(withContext(Dispatchers.IO) { run(params) }) }
    }

    class None
}