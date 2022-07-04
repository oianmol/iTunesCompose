package dev.baseio.itunesdata.network.model

import com.google.gson.annotations.SerializedName
import dev.baseio.itunesdomain.models.DMiTunesAlbum
import java.util.*

data class ITunesAlbum(val feed: Feed? = null)

data class ITunesAlbumMultipleEntries(val feed: Feeds? = null)

data class Feeds(val author: Author? = null, val entry: List<FeedEntry>? = null)

data class Feed(val author: Author? = null, val entry: FeedEntry? = null)

data class FeedEntry(
    @SerializedName("im:name") val name: ObWithLabel? = null,
    @SerializedName("im:image") val image: List<ObWithLabel>? = null,
    @SerializedName("im:itemCount") val itemCount: ObWithLabel? = null,
    @SerializedName("im:price") val itemPrice: ObWithLabel? = null,
    @SerializedName("title") val title: ObWithLabel? = null,
    @SerializedName("link") val link: ObWithLabel? = null,
    @SerializedName("id") val id: ObWithLabel? = null,
    @SerializedName("im:artist") val imArtist: ObWithLabel? = null
)

fun FeedEntry.toDMiTunesAlbum(): DMiTunesAlbum {
    val albumArt = this.image?.lastOrNull()?.label ?: "NA"
    val artistName = this.imArtist?.label ?: "NA"
    val id = this.id?.attributes?.id ?: UUID.randomUUID().toString()
    return DMiTunesAlbum(
        albumArt = albumArt,
        artistName = artistName,
        id = id,
        label = this.title?.label ?: "NA",
        price = this.itemPrice?.label ?: "NA"
    )
}

data class Author(val name: ObWithLabel? = null, val uri: ObWithLabel? = null)

data class ObWithLabel(val label: String? = null, val attributes: ITunesAttribute? = null)

data class ITunesAttribute(
    val height: String? = null,
    val amount: String? = null,
    val currency: String? = null,
    val rel: String? = null,
    val type: String? = null,
    val href: String? = null,
    @SerializedName("im:id") val id: String? = null
)