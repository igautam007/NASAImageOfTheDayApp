package com.gautamgelani.nasaopenapiimages.di

import android.annotation.SuppressLint
import android.content.Context
import com.gautamgelani.nasaopenapiimages.constant.APIConstant
import com.gautamgelani.nasaopenapiimages.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("networkInterceptor")
    fun getInterceptor(@ApplicationContext context: Context): Interceptor = Interceptor { chain ->
        val builder = chain.request()
            .newBuilder()
            .addHeader("Cache-Control", "no-cache")
        chain.proceed(builder.build())
    }

    @Singleton
    @Provides
    @SuppressLint("TrustAllX509TrustManager")
    fun getUnsafeOkHttpClient(
        @Named("networkInterceptor") networkInterceptor: Interceptor
    ): OkHttpClient = try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(
            @SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory
        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { _, _ -> true }
        builder.connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(networkInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .cache(null)
            .build()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }

    @Singleton
    @Provides
    fun getAPIInterface(unsafeOkHttpClient: OkHttpClient): ApiInterface = Retrofit.Builder()
        .baseUrl(APIConstant.API_BASE_URL)
        .client(unsafeOkHttpClient) //.addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiInterface::class.java)

}