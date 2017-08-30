package org.superbiz.moviefun.moviesapi

import org.springframework.stereotype.Component

import java.util.Arrays.asList

@Component
class MovieFixtures {

    fun load(): List<MovieInfo> {
        return asList(
                MovieInfo(null, "Wedding Crashers", "David Dobkin", "Comedy", 7, 2005),
                MovieInfo(null, "Starsky & Hutch", "Todd Phillips", "Action", 6, 2004),
                MovieInfo(null, "Shanghai Knights", "David Dobkin", "Action", 6, 2003),
                MovieInfo(null, "I-Spy", "Betty Thomas", "Adventure", 5, 2002),
                MovieInfo(null, "The Royal Tenenbaums", "Wes Anderson", "Comedy", 8, 2001),
                MovieInfo(null, "Zoolander", "Ben Stiller", "Comedy", 6, 2001),
                MovieInfo(null, "Shanghai Noon", "Tom Dey", "Comedy", 7, 2000)
        )
    }
}
