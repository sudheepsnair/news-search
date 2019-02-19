package com.ss.search.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListingResponse(
    @SerializedName("status")
    @Expose
    var status: String? = null,
    @SerializedName("response")
    @Expose
    var newsResponse: NewsResponse? = null
)

data class NewsResponse(
    @SerializedName("docs")
    @Expose
    var news: List<News>? = null
)

data class News(
    @SerializedName("web_url")
    @Expose
    var webUrl: String? = null,
    @SerializedName("snippet")
    @Expose
    var snippet: String? = null,
    @SerializedName("lead_paragraph")
    @Expose
    var leadParagraph: String? = null,
    @SerializedName("print_page")
    @Expose
    var printPage: String? = null,
    @SerializedName("source")
    @Expose
    var source: String? = null,
    @SerializedName("multimedia")
    @Expose
    var multimedia: List<Multimedia>? = null,
    @SerializedName("headline")
    @Expose
    var headline: Headline? = null,
    @SerializedName("pub_date")
    @Expose
    var pubDate: String? = null,
    @SerializedName("document_type")
    @Expose
    var documentType: String? = null,
    @SerializedName("news_desk")
    @Expose
    var newsDesk: String? = null,
    @SerializedName("section_name")
    @Expose
    var sectionName: String? = null,
    @SerializedName("type_of_material")
    @Expose
    var typeOfMaterial: String? = null,
    @SerializedName("_id")
    @Expose
    var id: String? = null,
    @SerializedName("word_count")
    @Expose
    var wordCount: Int? = null,
    @SerializedName("score")
    @Expose
    var score: Int? = null,
    @SerializedName("uri")
    @Expose
    var uri: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Multimedia),
        parcel.readParcelable(Headline::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(webUrl)
        parcel.writeString(snippet)
        parcel.writeString(leadParagraph)
        parcel.writeString(printPage)
        parcel.writeString(source)
        parcel.writeTypedList(multimedia)
        parcel.writeParcelable(headline, flags)
        parcel.writeString(pubDate)
        parcel.writeString(documentType)
        parcel.writeString(newsDesk)
        parcel.writeString(sectionName)
        parcel.writeString(typeOfMaterial)
        parcel.writeString(id)
        parcel.writeValue(wordCount)
        parcel.writeValue(score)
        parcel.writeString(uri)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<News> {
        override fun createFromParcel(parcel: Parcel): News {
            return News(parcel)
        }

        override fun newArray(size: Int): Array<News?> {
            return arrayOfNulls(size)
        }
    }
}


data class Headline(
    @SerializedName("main")
    @Expose
    var main: String? = null,
    @SerializedName("print_headline")
    @Expose
    var printHeadline: String? = null

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(main)
        parcel.writeString(printHeadline)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Headline> {
        override fun createFromParcel(parcel: Parcel): Headline {
            return Headline(parcel)
        }

        override fun newArray(size: Int): Array<Headline?> {
            return arrayOfNulls(size)
        }
    }
}


data class Multimedia(

    @SerializedName("subtype")
    @Expose
    var subtype: String? = null,
    @SerializedName("url")
    @Expose
    var url: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(subtype)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Multimedia> {
        override fun createFromParcel(parcel: Parcel): Multimedia {
            return Multimedia(parcel)
        }

        override fun newArray(size: Int): Array<Multimedia?> {
            return arrayOfNulls(size)
        }
    }
}



