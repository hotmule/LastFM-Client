package ru.hotmule.lastik.data.local

import kotlin.Boolean
import kotlin.String

data class Track(
  val id: String,
  val artistId: String,
  val albumId: String,
  val name: String?,
  val loved: Boolean
) {
  override fun toString(): String = """
  |Track [
  |  id: $id
  |  artistId: $artistId
  |  albumId: $albumId
  |  name: $name
  |  loved: $loved
  |]
  """.trimMargin()
}
