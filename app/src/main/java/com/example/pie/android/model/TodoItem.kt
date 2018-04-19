package com.example.pie.android.model

import android.os.Parcel
import com.example.pie.android.util.KParcelable
import com.example.pie.android.util.parcelableCreator
import com.google.gson.annotations.Expose

data class TodoItem (

    /**
     * The Server ID
     */
    @Expose(serialize = false)
    var id: Int = 0,

    /**
     * The Client ID - Should never be sent to the server.
     */
    var _id: Long = 0,

    @Expose
    var task: String? = null,

    @Expose
    var done: Boolean = false
): KParcelable {

    constructor(parcel: Parcel): this(
            id = parcel.readInt(),
            _id = parcel.readLong(),
            task = parcel.readString(),
            done = parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        with(parcel) {
            writeInt(id)
            writeLong(_id)
            writeString(task)
            writeByte(if (done) 1 else 0)
        }
    }

    fun complete() {
        this.done = true
    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::TodoItem)
    }
}