package org.superbiz.moviefun.moviesapi

class MovieInfo {
    var id: Long? = null
    var title: String? = null
    var director: String? = null
    var genre: String? = null
    var rating: Int = 0
    var year: Int = 0

    constructor() {}

    constructor(id: Long?, title: String, director: String, genre: String, rating: Int, year: Int) {
        this.id = id
        this.title = title
        this.director = director
        this.genre = genre
        this.rating = rating
        this.year = year
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val movieInfo = o as MovieInfo?

        if (id !== movieInfo!!.id) return false
        if (year != movieInfo!!.year) return false
        if (rating != movieInfo.rating) return false
        if (if (director != null) director != movieInfo.director else movieInfo.director != null) return false
        if (if (title != null) title != movieInfo.title else movieInfo.title != null) return false
        return if (genre != null) genre == movieInfo.genre else movieInfo.genre == null
    }

    override fun hashCode(): Int {
        var result = (id!! xor id!!.ushr(32)).toInt()
        result = 31 * result + (director?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + year
        result = 31 * result + (genre?.hashCode() ?: 0)
        result = 31 * result + rating
        return result
    }

    override fun toString(): String {
        return "MovieInfo{" +
                "id=" + id +
                ", director='" + director + '\'' +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                '}'
    }
}
