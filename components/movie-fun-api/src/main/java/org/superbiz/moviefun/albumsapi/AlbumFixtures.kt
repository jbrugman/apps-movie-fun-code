package org.superbiz.moviefun.albumsapi

import org.springframework.stereotype.Component

import java.util.Arrays.asList

@Component
class AlbumFixtures {

    fun load(): List<AlbumInfo> {
        return asList(
                AlbumInfo(null, "Massive Attack", "Mezzanine", 1998, 9),
                AlbumInfo(null, "Radiohead", "OK Computer", 1997, 8),
                AlbumInfo(null, "Radiohead", "Kid A", 2000, 9)
        )
    }
}
