package org.superbiz.moviefun.albumsapi

class AlbumInfo {

    val id: Long?
    val artist: String?
    val title: String?
    val year: Int
    val rating: Int

    constructor() {}

    constructor(id: Long?, artist: String, title: String, year: Int, rating: Int) {
        this.id = id
        this.artist = artist
        this.title = title
        this.year = year
        this.rating = rating
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val albumInfo = o as AlbumInfo?

        if (year != albumInfo!!.year) return false
        if (rating != albumInfo.rating) return false
        if (if (id != null) id != albumInfo.id else albumInfo.id != null) return false
        if (if (artist != null) artist != albumInfo.artist else albumInfo.artist != null) return false
        return if (title != null) title == albumInfo.title else albumInfo.title == null
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (artist?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + year
        result = 31 * result + rating
        return result
    }

    override fun toString(): String {
        return "AlbumInfo{" +
                "id=" + id +
                ", artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                '}'
    }
}
