package org.superbiz.moviefun.blobstore

import org.apache.tika.Tika
import org.apache.tika.io.IOUtils

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.Optional


class FileStore : BlobStore {

    private val tika = Tika()


    @Throws(IOException::class)
    override fun put(blob: Blob) {
        val targetFile = File(blob.name)

        targetFile.delete()
        targetFile.parentFile.mkdirs()
        targetFile.createNewFile()


        FileOutputStream(targetFile).use { outputStream -> IOUtils.copy(blob.inputStream, outputStream) }
    }

    @Throws(IOException::class)
    override fun get(name: String): Optional<Blob> {
        val file = File(name)

        return if (!file.exists()) {
            Optional.empty()
        } else Optional.of(Blob(
                name,
                FileInputStream(file),
                tika.detect(file)
        ))

    }
}
