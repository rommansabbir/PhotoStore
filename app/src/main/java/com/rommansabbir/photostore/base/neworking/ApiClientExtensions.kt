package com.rommansabbir.photostore.base.neworking

import android.content.Context
import com.rommansabbir.photostore.base.*
import retrofit2.Call

/**
 * Execute a new API request to the server side.
 * If the request is successful then transform the response body to the given object.
 * Else, if something goes wrong throw return [Failure].
 *
 * @param context [Context].
 * @param call [Call] request that need to be executed.
 * @param transform if you want to transform the object into another object.
 * @param default to return the response body as the provided by the object type.
 * @param postRequest if you want to do something more with the response object.
 *
 * @return [Either].
 */
fun <T, R> executeAPIRequest(
    context: Context,
    call: Call<T>,
    transform: (T) -> R,
    default: T,
    postRequest: (R) -> Unit = {},
): Either<Failure, R> {
    return try {
        val response = call.execute()
        when (response.isSuccessful) {
            true -> {
                val transformed = transform((response.body() ?: default))
                postRequest(transformed)
                Either.Right(transformed)
            }
            false -> Either.Left(
                getFailureTypeAccordingToHTTPCode(
                    context,
                    response.code()
                )
            )
        }
    } catch (exception: Throwable) {
        exception.printStackTrace()
        Either.Left(ExceptionF())
    }
}

/**
 * Return a new [Failure] according to the HTTP code.
 *
 * @param context [Context].
 * @param httpCode http code.
 *
 * @return [Failure].
 */
internal fun getFailureTypeAccordingToHTTPCode(context: Context, httpCode: Int): Failure {
    return when (httpCode) {
        401 -> return UnauthorizedError()
        in 402..409 -> return CanNotConnectToServer()
        in 500..509 -> return CanNotConnectToServer()
        else -> CanNotConnectToServer()
    }
}