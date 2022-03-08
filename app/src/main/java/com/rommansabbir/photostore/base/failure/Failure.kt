package com.rommansabbir.photostore.base.failure

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */

const val NO_INTERNET = "Device is not connected to the internet"
const val NOT_LOGGED_IN = "You are not logged in"
const val SOMETHING_WENT_WRONG = "Something went wrong"

sealed class Failure

/*Networking Specific*/
class NetworkConnectionError(val message: String = NO_INTERNET) : Failure()
class UnauthorizedError(val message: String = NOT_LOGGED_IN) : Failure()
class CanNotConnectToServer(val message : String = "Can't connect to server."): Failure()

/*Exception/Errors*/
class ExceptionF(val message: String = SOMETHING_WENT_WRONG) : Failure()


/*Feature Specific*/
sealed class FeatureFailure : Failure()
class APIKeyMissing(val message: String = "API Key Missing") : FeatureFailure()