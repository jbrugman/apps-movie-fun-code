package org.superbiz.moviefun

import org.junit.Test
import org.springframework.web.client.RestTemplate

import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat

class SmokeTest {

    @Test
    fun smokeTest() {
        val restTemplate = RestTemplate()

        val homePage = restTemplate.getForObject<String>(url("/"), String::class.java)

        assertThat(homePage, containsString("Please select one of the following links:"))

        val setupPage = restTemplate.getForObject<String>(url("/setup"), String::class.java)

        assertThat(setupPage, containsString("Wedding Crashers"))
        assertThat(setupPage, containsString("Starsky & Hutch"))
        assertThat(setupPage, containsString("Shanghai Knights"))
        assertThat(setupPage, containsString("I-Spy"))
        assertThat(setupPage, containsString("The Royal Tenenbaums"))

        val movieFunPage = restTemplate.getForObject<String>(url("/moviefun"), String::class.java)

        assertThat(movieFunPage, containsString("Wedding Crashers"))
        assertThat(movieFunPage, containsString("David Dobkin"))
    }

    private fun url(path: String): String {
        var baseUrl = "http://localhost:8080/"
        val envUrl = System.getenv("MOVIE_FUN_URL")

        if (envUrl != null && !envUrl.isEmpty()) {
            baseUrl = envUrl
        }

        return baseUrl + path
    }
}
