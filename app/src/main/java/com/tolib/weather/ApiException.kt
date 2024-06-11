package com.tolib.weather

class ApiException(val error: Int, override val message: String) : Exception(message)