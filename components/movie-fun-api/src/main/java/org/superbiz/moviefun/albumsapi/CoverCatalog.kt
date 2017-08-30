package org.superbiz.moviefun.albumsapi

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.superbiz.moviefun.blobstore.Blob
import org.superbiz.moviefun.blobstore.BlobStore

import java.io.IOException
import java.io.InputStream

import java.lang.String.format
import java.util.function.Supplier

@Component
 class CoverCatalog( val blobStore: BlobStore) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Throws(IOException::class)
    fun uploadCover(albumId: Long, inputStream: InputStream, contentType: String) {
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
    fun getCover(albumId: Long): Blob {

        println("blobStore: $blobStore")

        val maybeCoverBlob = blobStore[coverBlobName(albumId)]
        return maybeCoverBlob.orElseGet(Supplier<Blob>({
            val classLoader = this.javaClass.classLoader
            val input = classLoader.getResourceAsStream("default-cover.jpg")
            Blob("default-cover", input, MediaType.IMAGE_JPEG_VALUE)
        }))
    }

    fun buildDefaultCoverBlob(albumId: Long): Blob {
        val classLoader = javaClass.classLoader
        val input = classLoader.getResourceAsStream("default-cover.jpg")
        return Blob("default-cover", input, MediaType.IMAGE_JPEG_VALUE)
    }

    private fun coverBlobName(albumId: Long): String {
        return format("covers/%d", albumId)
    }

}