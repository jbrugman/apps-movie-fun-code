package org.superbiz.moviefun

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.superbiz.moviefun.albumsapi.AlbumFixtures
import org.superbiz.moviefun.albumsapi.AlbumInfo
import org.superbiz.moviefun.albumsapi.AlbumsClient
import org.superbiz.moviefun.moviesapi.MovieFixtures
import org.superbiz.moviefun.moviesapi.MovieInfo
import org.superbiz.moviefun.moviesapi.MoviesClient

@Controller
class SetupController(private val moviesClient: MoviesClient, private val albumsClient: AlbumsClient, private val movieFixtures: MovieFixtures, private val albumFixtures: AlbumFixtures) {

    @GetMapping("/setup")
    fun setup(model: MutableMap<String, Any>): String {
        for (movie in movieFixtures.load()) {
            moviesClient.addMovie(movie)
        }

        for (album in albumFixtures.load()) {
            albumsClient.addAlbum(album)
        }

        model.put("movies", moviesClient.movies)
        model.put("albums", albumsClient.albums)

        return "setup"
    }
}
