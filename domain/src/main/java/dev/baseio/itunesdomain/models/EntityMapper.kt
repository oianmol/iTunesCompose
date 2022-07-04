package dev.baseio.itunesdomain.models

interface EntityMapper<Data, Domain> {
  fun mapToDomain(entity: Data): Domain
  fun mapToData(model: Domain): Data
}