package org.superbiz.moviefun.albumsapi

import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.client.RestOperations

import org.springframework.http.HttpMethod.GET

class AlbumsClient(private val albumsUrl: String, private val restOperations: RestOperations) {

    fun addAlbum(album: AlbumInfo) {
        restOperations.postForEntity(albumsUrl, album, AlbumInfo::class.java)
    }

    fun find(id: Long): AlbumInfo {
        return restOperations.getForEntity(albumsUrl + "/" + id, AlbumInfo::class.java).body
    }

    val albums: List<AlbumInfo>
        get() {
            val albumListType = object : ParameterizedTypeReference<List<AlbumInfo>>() {

            }

            return restOperations.exchange(albumsUrl, GET, null, albumListType).body
        }
}
