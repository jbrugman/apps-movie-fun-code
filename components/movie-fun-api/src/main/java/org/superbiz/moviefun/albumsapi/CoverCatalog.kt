package org.superbiz.moviefun.albumsapi

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.superbiz.moviefun.blobstore.Blob
import org.superbiz.moviefun.blobstore.BlobStore

import java.io.IOException
import java.io.InputStream
import java.util.Optional

import java.lang.String.format

class CoverCatalog(private val blobStore: BlobStore) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Throws(IOException::class)
    internal fun uploadCover(albumId: Long, inputStream: InputStream, contentType: String) {
        logger.debug("Uploading cover for album with id {}", albumId)

        val coverBlob = Blob(
                coverBlobName(albumId),
                inputStream,
                contentType
        )

        blobStore.put(coverBlob)
    }

    @HystrixCommand(fallbackMethod = "buildDefaultCoverBlob")
    @Throws(IOException::class)
    internal fun getCover(albumId: Long): Blob {
        val maybeCoverBlob = blobStore[coverBlobName(albumId)]

        return maybeCoverBlob.orElseGet(Supplier<Blob> { this.buildDefaultCoverBlob() })
    }

    internal fun buildDefaultCoverBlob(albumId: Long): Blob {
        return buildDefaultCoverBlob()
    }

    private fun buildDefaultCoverBlob(): Blob {
        val classLoader = javaClass.classLoader
        val input = classLoader.getResourceAsStream("default-cover.jpg")

        return Blob("default-cover", input, MediaType.IMAGE_JPEG_VALUE)
    }

    private fun coverBlobName(albumId: Long): String {
        return format("covers/%d", albumId)
    }

}