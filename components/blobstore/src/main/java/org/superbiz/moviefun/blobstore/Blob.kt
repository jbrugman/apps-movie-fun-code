package org.superbiz.moviefun.blobstore

import java.io.InputStream

class Blob(val name: String, val inputStream: InputStream, val contentType: String)
