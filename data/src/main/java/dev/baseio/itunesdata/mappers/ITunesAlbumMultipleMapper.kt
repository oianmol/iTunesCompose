package dev.baseio.itunesdata.mappers

import dev.baseio.itunesdata.network.model.ITunesAlbumMultipleEntries
import dev.baseio.itunesdata.network.model.toDMiTunesAlbum
import dev.baseio.itunesdomain.models.DMiTunesAlbum
import dev.baseio.itunesdomain.models.EntityMapper

class ITunesAlbumMultipleMapper : EntityMapper<ITunesAlbumMultipleEntries, List<DMiTunesAlbum>> {
    override fun mapToDomain(entity: ITunesAlbumMultipleEntries): List<DMiTunesAlbum> {
        return entity.feed?.entry?.map { entry ->
            entry.toDMiTunesAlbum()
        } ?: emptyList()
    }

    override fun mapToData(model: List<DMiTunesAlbum>): ITunesAlbumMultipleEntries {
        throw RuntimeException("Not required yet.")
    }
}
