package org.superbiz.moviefun

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.hystrix.EnableHystrix
import org.springframework.context.annotation.Bean
import org.superbiz.moviefun.albumsapi.CoverCatalog
import org.superbiz.moviefun.blobstore.BlobStore
import org.superbiz.moviefun.blobstore.S3Store
import org.superbiz.moviefun.moviesapi.MovieServlet

@EnableHystrix
@EnableEurekaClient
@SpringBootApplication
class Application {

    @Bean
    fun actionServletRegistration(movieServlet: MovieServlet): ServletRegistrationBean {
        return ServletRegistrationBean(movieServlet, "/moviefun/*")
    }

    @Value("\${s3.endpointUrl}") internal var s3EndpointUrl: String? = null
    @Value("\${s3.accessKey}") internal var s3AccessKey: String? = null
    @Value("\${s3.secretKey}") internal var s3SecretKey: String? = null
    @Value("\${s3.bucketName}") internal var s3BucketName: String? = null

    @Bean
    fun blobStore(): BlobStore {
        val credentials = BasicAWSCredentials(s3AccessKey!!, s3SecretKey!!)
        val s3Client = AmazonS3Client(credentials)
        s3Client.setEndpoint(s3EndpointUrl!!)

        return S3Store(s3Client, s3BucketName!!)
    }

    @Bean
    fun coverCatalog(blobStore: BlobStore): CoverCatalog {
        return CoverCatalog(blobStore)
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }
}
