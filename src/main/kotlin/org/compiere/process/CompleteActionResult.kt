package org.compiere.process

data class CompleteActionResult<T>(val newStatusName: String, val result: T? = null) {
    constructor(_newStatusName: String) : this(_newStatusName, null)
}