package ru.hotmule.lastik.data.local

import kotlin.Int
import kotlin.String

data class ArtistTop(
  val name: String?,
  val rank: Int?,
  val playCount: Int?,
  val lowResImage: String?
) {
  override fun toString(): String = """
  |ArtistTop [
  |  name: $name
  |  rank: $rank
  |  playCount: $playCount
  |  lowResImage: $lowResImage
  |]
  """.trimMargin()
}