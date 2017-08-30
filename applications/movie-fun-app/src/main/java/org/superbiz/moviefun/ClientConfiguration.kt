package org.superbiz.moviefun

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestOperations
import org.superbiz.moviefun.albumsapi.AlbumsClient
import org.superbiz.moviefun.moviesapi.MoviesClient

@Configuration
class ClientConfiguration {

    @Bean
    fun albumsClient(restOperations: RestOperations): AlbumsClient {
        return AlbumsClient("//album-service/albums", restOperations)
    }

    @Bean
    fun moviesClient(restOperations: RestOperations): MoviesClient {
        return MoviesClient("//movie-service/movies", restOperations)
    }
}
