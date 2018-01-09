package com.example.pie.android.model

import com.google.gson.annotations.Expose

data class TodoItem (

    /**
     * The Server ID
     */
    @Expose(serialize = false)
    var id: Int = 0,

    /**
     * The Cient ID - Should never be sent to the server.
     */
    var _id: Long = 0,

    @Expose
    var task: String? = null,

    @Expose

    var done: Boolean = false
) {
    fun complete() {
        this.done = true
    }
}