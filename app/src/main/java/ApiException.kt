class ApiException(val error: Int, override val message: String) : Exception(message)