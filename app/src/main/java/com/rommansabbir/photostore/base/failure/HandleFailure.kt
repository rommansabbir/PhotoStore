package com.rommansabbir.photostore.base.failure

import android.content.Context
import android.widget.Toast

fun handleFailure(context: Context, failure: Failure) {
    when (failure) {
        is CanNotConnectToServer -> showToast(context, failure.message)
        is ExceptionF -> showToast(context, failure.message)
        is APIKeyMissing -> showToast(context, failure.message)
        is NetworkConnectionError -> showToast(context, failure.message)
        is UnauthorizedError -> showToast(context, failure.message)
    }
}

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}