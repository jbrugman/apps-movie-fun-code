package org.superbiz.moviefun.movies

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/movies")
class MoviesController(private val moviesRepository: MoviesRepository) {

    @PostMapping
    fun addMovie(@RequestBody movie: Movie) {
        moviesRepository.addMovie(movie)
    }

    @DeleteMapping("/{movieId}")
    fun deleteMovieId(@PathVariable movieId: Long?) {
        moviesRepository.deleteMovieId(movieId!!)
    }

    @GetMapping("/count")
    fun count(
            @RequestParam(name = "field", required = false) field: String?,
            @RequestParam(name = "key", required = false) key: String?
    ): Int {
        if (field != null && key != null) {
            return moviesRepository.count(field, key)
        } else {
            return moviesRepository.countAll()
        }
    }

    @GetMapping
    fun find(
            @RequestParam(name = "field", required = false) field: String?,
            @RequestParam(name = "key", required = false) key: String?,
            @RequestParam(name = "start", required = false) start: Int?,
            @RequestParam(name = "pageSize", required = false) pageSize: Int?
    ): List<Movie> {
        if (field != null && key != null) {
            return moviesRepository.findRange(field, key, start!!, pageSize!!)
        } else if (start != null && pageSize != null) {
            return moviesRepository.findAll(start, pageSize)
        } else {
            return moviesRepository.movies
        }
    }
}
