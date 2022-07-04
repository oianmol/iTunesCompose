package dev.baseio.itunesdomain.models

data class DMiTunesAlbum(
    val id: String,
    val price: String,
    val albumArt: String,
    val artistName: String,
    val label: String,
    var isFav: Boolean = false
)