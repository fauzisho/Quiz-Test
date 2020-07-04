package id.com.common.data.remote.exception


open class ErrorException : RuntimeException {

    var code = -1

    constructor() : super("Oops, Something went wrong!")

    constructor(message: String) : super(message)

    constructor(code: Int, message: String) : super(message) {
        this.code = code
    }

    override val message: String?
        get() = if (super.message == "") "Unknown Error!" else super.message
}
