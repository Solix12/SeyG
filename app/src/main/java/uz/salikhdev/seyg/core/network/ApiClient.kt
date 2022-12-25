package uz.salikhdev.seyg.core.network

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.salikhdev.seyg.core.app.App
import uz.salikhdev.seyg.core.cache.LocalStorage
import uz.salikhdev.seyg.core.network.service.AuthService
import uz.salikhdev.seyg.core.network.service.ChatService
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object ApiClient {
    private val local = LocalStorage()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(getOkHttpClient())
            .baseUrl("https://a73e-213-230-70-26.in.ngrok.io")
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .build()


    }

    private fun getNoHeaderRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(getNoHeaderOkHttpClient())
            .baseUrl("https://a73e-213-230-70-26.in.ngrok.io")
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .build()


    }

    fun getAuthService(): AuthService {
        return getNoHeaderRetrofit().create(AuthService::class.java)
    }

    fun getChatService(): ChatService {
        return getRetrofit().create(ChatService::class.java)
    }

    private fun getNoHeaderOkHttpClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            // .sslSocketFactory(getSslSocketFactory(), getTrustManagers()[0] as X509TrustManager)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(getChuckerInterception())
            .addInterceptor(noHeaderInterceptor())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
           // .sslSocketFactory(getSslSocketFactory(), getTrustManagers()[0] as X509TrustManager)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(getChuckerInterception())
            .addInterceptor(interceptor())
            .build()
    }

    private fun getChuckerCollector(): ChuckerCollector {

        return ChuckerCollector(
            context = App.instance!!,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.FOREVER
        )

    }

    private fun noHeaderInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            val builder: Request.Builder = request.newBuilder()
            builder
                //.addHeader("Authorization", "Token ${local.token}")
                .header("Connection", "close")
                .addHeader("Content-type", "application/json")
            val response = chain.proceed(builder.build())
            response
        }
    }


    private fun interceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            val builder: Request.Builder = request.newBuilder()
            builder
                .addHeader("Authorization", "Token ${local.token}")
                .header("Connection", "close")
                .addHeader("Content-type", "application/json")
            val response = chain.proceed(builder.build())
            response
        }
    }

    private fun getChuckerInterception(): ChuckerInterceptor {

        return ChuckerInterceptor.Builder(App.instance!!)
            .collector(getChuckerCollector())
            .maxContentLength(250_000L)
            .alwaysReadResponseBody(true)
            .build()
    }

    private fun getGson(): Gson = GsonBuilder().setLenient().create()


    private fun getSslSocketFactory(): SSLSocketFactory {
        // Install the all-trusting trust manager
        val sslContext: SSLContext = SSLContext.getInstance("SSL")
        sslContext.init(null, getTrustManagers(), SecureRandom())
        return sslContext.socketFactory
    }

    private fun getTrustManagers(): Array<TrustManager> {
        val trustAllCerts: Array<TrustManager> = arrayOf(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?,
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?,
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )
        return trustAllCerts
    }

}