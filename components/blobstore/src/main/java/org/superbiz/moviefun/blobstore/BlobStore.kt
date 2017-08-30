package org.superbiz.moviefun.blobstore

import java.io.IOException
import java.util.Optional

interface BlobStore {

    @Throws(IOException::class)
    fun put(blob: Blob)

    @Throws(IOException::class)
    operator fun get(name: String): Optional<Blob>
}
