package com.solo.snews.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class NewsFields() : Parcelable, RealmObject() {
    @SerializedName("thumbnail")
    var thumbnail = ""
    @SerializedName("body")
    var content = ""

    constructor(parcel: Parcel) : this() {
        thumbnail = parcel.readString()
        content = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(thumbnail)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsFields> {
        override fun createFromParcel(parcel: Parcel): NewsFields {
            return NewsFields(parcel)
        }

        override fun newArray(size: Int): Array<NewsFields?> {
            return arrayOfNulls(size)
        }
    }


}