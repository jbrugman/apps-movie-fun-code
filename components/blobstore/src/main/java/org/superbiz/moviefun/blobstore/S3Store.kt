package org.superbiz.moviefun.blobstore

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.util.IOUtils
import org.apache.tika.Tika

import java.io.ByteArrayInputStream
import java.io.IOException
import java.util.Optional

class S3Store(private val s3: AmazonS3, private val bucketName: String) : BlobStore {
    private val tika = Tika()

    @Throws(IOException::class)
    override fun put(blob: Blob) {
        s3.putObject(bucketName, blob.name, blob.inputStream, ObjectMetadata())
    }

    @Throws(IOException::class)
    override fun get(name: String): Optional<Blob> {
        if (!s3.doesObjectExist(bucketName, name)) {
            return Optional.empty()
        }

        val s3Object = s3.getObject(bucketName, name)
        val content = s3Object.objectContent

        val bytes = IOUtils.toByteArray(content)

        return Optional.of(Blob(
                name,
                ByteArrayInputStream(bytes),
                tika.detect(bytes)
        ))
    }
}
