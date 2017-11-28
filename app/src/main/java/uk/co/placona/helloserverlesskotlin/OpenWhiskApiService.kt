package uk.co.placona.helloserverlesskotlin

import io.reactivex.*
import okhttp3.*
import okhttp3.logging.*
import retrofit2.*
import retrofit2.adapter.rxjava2.*
import retrofit2.converter.gson.*
import retrofit2.http.*

interface OpenWhiskApiService {

    @POST("kotlinconf.json")
    fun nameEchoer(@Body body: Model.Request): Observable<Model.Result>

    companion object {
        fun create(): OpenWhiskApiService {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .baseUrl(BuildConfig.OPEN_WHISK_FUNCTION_URI)
                    .build()

            return retrofit.create(OpenWhiskApiService::class.java)
        }
    }

}