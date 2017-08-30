package org.superbiz.moviefun

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.hystrix.EnableHystrix
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.superbiz.moviefun.albumsapi.CoverCatalog
import org.superbiz.moviefun.blobstore.BlobStore
import org.superbiz.moviefun.blobstore.S3Store
import org.superbiz.moviefun.moviesapi.MovieServlet

@EnableHystrix
@EnableEurekaClient
@SpringBootApplication
class Application {


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }
}

@Configuration
class AppConfig {
    @Value("\${s3.endpointUrl}") private var s3EndpointUrl: String? = null
    @Value("\${s3.accessKey}") private var s3AccessKey: String? = null
    @Value("\${s3.secretKey}") private var s3SecretKey: String? = null
    @Value("\${s3.bucketName}") private var s3BucketName: String? = null

    @Bean
    fun blobStore(): BlobStore {
        val credentials = BasicAWSCredentials(s3AccessKey!!, s3SecretKey!!)
        val s3Client = AmazonS3Client(credentials)
        s3Client.setEndpoint(s3EndpointUrl!!)

        return S3Store(s3Client, s3BucketName!!)
    }

    @Bean
    fun actionServletRegistration(movieServlet: MovieServlet): ServletRegistrationBean {
        return ServletRegistrationBean(movieServlet, "/moviefun/*")
    }
}