package com.tickledmedia.tmrohit.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
class ResponseItem {

    @PrimaryKey(autoGenerate = true)
    var id : Int? = null

    @SerializedName("author")
    @Expose
    var author: String? = ""

    @SerializedName("name")
    @Expose
    var name: String? = ""

    @SerializedName("avatar")
    @Expose
    var avatar: String? = ""

    @SerializedName("url")
    @Expose
    var url: String? = ""

    @SerializedName("description")
    @Expose
    var description: String? = ""

    @SerializedName("language")
    @Expose
    var language: String? = ""

    @SerializedName("languageColor")
    @Expose
    var languageColor: String? = ""

    @SerializedName("stars")
    @Expose
    var stars: Int = 0

    @SerializedName("forks")
    @Expose
    var forks: Int = 0

    @SerializedName("currentPeriodStars")
    @Expose
    var currentPeriodStars: Int = 0


    var lastUpdatedTime : Long = System.currentTimeMillis()


}