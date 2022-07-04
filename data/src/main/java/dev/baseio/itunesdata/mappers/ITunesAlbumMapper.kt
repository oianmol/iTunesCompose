package dev.baseio.itunesdata.mappers

import dev.baseio.itunesdata.network.model.ITunesAlbum
import dev.baseio.itunesdata.network.model.toDMiTunesAlbum
import dev.baseio.itunesdomain.models.DMiTunesAlbum
import dev.baseio.itunesdomain.models.EntityMapper

class ITunesAlbumMapper : EntityMapper<ITunesAlbum, List<DMiTunesAlbum>> {
    override fun mapToDomain(entity: ITunesAlbum): List<DMiTunesAlbum> {
        entity.feed?.entry?.toDMiTunesAlbum()?.let {
            return listOf(it)
        }
        return emptyList()
    }

    override fun mapToData(model: List<DMiTunesAlbum>): ITunesAlbum {
        throw RuntimeException("Not required yet.")
    }
}