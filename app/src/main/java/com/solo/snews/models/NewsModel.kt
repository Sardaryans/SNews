package com.solo.snews.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class NewsModel() : Parcelable, RealmObject(){
    @SerializedName("webTitle")
    var title = ""

    @SerializedName("webPublicationDate")
    var date = ""

    @SerializedName("webUrl")
    var webUrl = ""

    @SerializedName("fields")
    var newsFields: NewsFields? = null

    var isSelected = false

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        date = parcel.readString()
        webUrl = parcel.readString()
        newsFields = parcel.readParcelable(NewsFields::class.java.classLoader)
        isSelected = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(date)
        parcel.writeString(webUrl)
        parcel.writeParcelable(newsFields, flags)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NewsModel

        if (title != other.title) return false
        if (date != other.date) return false
        if (webUrl != other.webUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + webUrl.hashCode()
        return result
    }

    companion object CREATOR : Parcelable.Creator<NewsModel> {
        override fun createFromParcel(parcel: Parcel): NewsModel {
            return NewsModel(parcel)
        }

        override fun newArray(size: Int): Array<NewsModel?> {
            return arrayOfNulls(size)
        }
    }



}