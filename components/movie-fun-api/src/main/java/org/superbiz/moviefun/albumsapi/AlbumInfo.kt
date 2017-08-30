package org.superbiz.moviefun.albumsapi

class AlbumInfo(var id: Long?, var artist: String, var title: String, var year: Int, var rating: Int) {

     override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as AlbumInfo

        if (year != other.year) return false
        if (rating != other.rating) return false
        if (if (id != null) id != other.id else other.id != null) return false
        if (if (artist != null) artist != other.artist else other.artist != null) return false
        return if (title != null) title == other.title else other.title == null
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