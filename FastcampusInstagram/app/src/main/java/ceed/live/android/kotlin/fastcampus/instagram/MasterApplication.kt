package ceed.live.android.kotlin.fastcampus.instagram

import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MasterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    fun createRetrofit() {
        val header = Interceptor {
            val original = it.request()
            val request = original.newBuilder()
                .header("Authorization", "")
                .build()
            it.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(header)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

//        service = retrofit.create(RetrofitService::class.java)
    }

    fun checkLogin(): Boolean {
        return true
    }

}