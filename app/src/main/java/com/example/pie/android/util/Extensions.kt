package com.example.pie.android.util

import android.os.Parcel
import android.os.Parcelable

interface KParcelable: Parcelable {
    override fun describeContents() = 0
}

inline fun <reified T: Parcelable> parcelableCreator(
        crossinline creator: (Parcel) -> T?
): Parcelable.Creator<T> = object: Parcelable.Creator<T> {
    override fun createFromParcel(parcel: Parcel) = creator(parcel)

    override fun newArray(size: Int) = arrayOfNulls<T?>(size)

}
