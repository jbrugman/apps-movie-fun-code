package org.superbiz.moviefun.albums

import org.junit.Test

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat

class AlbumTest {

    @Test
    fun testIsEquivalent() {
        val persisted = Album("Radiohead", "OK Computer", 1997, 8)
        persisted.id = 10L

        val sameFromCsv = Album("Radiohead", "OK Computer", 1997, 9)
        assertThat(persisted.isEquivalent(sameFromCsv), `is`(true))

        val otherFromCsv = Album("Radiohead", "Kid A", 2000, 9)
        assertThat(persisted.isEquivalent(otherFromCsv), `is`(false))
    }
}
