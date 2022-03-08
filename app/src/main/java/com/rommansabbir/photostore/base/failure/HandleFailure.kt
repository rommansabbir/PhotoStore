package com.rommansabbir.photostore.base.failure

import android.content.Context
import android.widget.Toast

/**
 * Handle app [Failure]s.
 *
 * @param context [Context].
 * @param failure [Failure].
 */
fun handleFailure(context: Context, failure: Failure) {
    when (failure) {
        is CanNotConnectToServer -> showToast(context, failure.message)
        is ExceptionF -> showToast(context, failure.message)
        is APIKeyMissing -> showToast(context, failure.message)
        is NetworkConnectionError -> showToast(context, failure.message)
        is UnauthorizedError -> showToast(context, failure.message)
    }
}

/**
 * Show a toast.
 *
 * @param context [Context].
 * @param msg Message that will be shown.
 */
fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}