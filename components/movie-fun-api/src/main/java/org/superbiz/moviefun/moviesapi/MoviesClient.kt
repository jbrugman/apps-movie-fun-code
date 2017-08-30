package org.superbiz.moviefun.moviesapi

import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.client.RestOperations
import org.springframework.web.util.UriComponentsBuilder

import org.springframework.http.HttpMethod.GET

class MoviesClient(private val moviesUrl: String, private val restOperations: RestOperations) {

    fun addMovie(movie: MovieInfo) {
        restOperations.postForEntity(moviesUrl, movie, MovieInfo::class.java)
    }

    fun deleteMovieId(movieId: Long?) {
        restOperations.delete(moviesUrl + "/" + movieId)
    }

    fun countAll(): Int {
        return restOperations.getForObject(moviesUrl + "/count", Int::class.java)
    }


    fun count(field: String, key: String): Int {
        val builder = UriComponentsBuilder.fromUriString(moviesUrl + "/count")
                .queryParam("field", field)
                .queryParam("key", key)

        return restOperations.getForObject(builder.toUriString(), Int::class.java)
    }


    fun findAll(start: Int, pageSize: Int): List<MovieInfo> {
        val builder = UriComponentsBuilder.fromUriString(moviesUrl)
                .queryParam("start", start)
                .queryParam("pageSize", pageSize)

        return restOperations.exchange(builder.toUriString(), GET, null, movieListType).body
    }

    fun findRange(field: String, key: String, start: Int, pageSize: Int): List<MovieInfo> {
        val builder = UriComponentsBuilder.fromUriString(moviesUrl)
                .queryParam("field", field)
                .queryParam("key", key)
                .queryParam("start", start)
                .queryParam("pageSize", pageSize)

        return restOperations.exchange(builder.toUriString(), GET, null, movieListType).body
    }

    val movies: List<MovieInfo>
        get() = restOperations.exchange(moviesUrl, GET, null, movieListType).body

    companion object {

        private val movieListType = object : ParameterizedTypeReference<List<MovieInfo>>() {

        }
    }
}
