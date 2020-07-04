package id.com.common.data.remote.exception

class ErrorInvalidUserException : ErrorException("Invalid User, Please Login") {
    init {
        code = 401
    }

}
