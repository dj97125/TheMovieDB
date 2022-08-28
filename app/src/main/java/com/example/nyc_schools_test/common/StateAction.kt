package com.example.nyc_schools_test.common

sealed class StateAction {
    class SUCCESS<T>(val response: T, val message: String) : StateAction()
    class ERROR(val error: Exception) : StateAction()
}
