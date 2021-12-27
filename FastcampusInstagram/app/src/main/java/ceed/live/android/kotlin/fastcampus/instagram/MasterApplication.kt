package ceed.live.android.kotlin.fastcampus.instagram

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import ceed.live.android.kotlin.fastcampus.instagram.http.RetrofitService
import ceed.live.android.kotlin.fastcampus.instagram.logger.Log4k
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MasterApplication : Application() {

    lateinit var service: RetrofitService

    override fun onCreate() {
        Log4k.e("onCreate")
        super.onCreate()

        Stetho.initializeWithDefaults(this)
        createRetrofit()
    }

    fun createRetrofit() {
        val header = Interceptor {
            val original = it.request()
            when {
                checkLogin() -> {
                    val token = getUserToken()
                    token?.let { authorization ->
                        val request = original.newBuilder()
                            .header("Authorization", "token $authorization")
                            .build()
                        it.proceed(request)
                    }
                }
                else -> {
                    it.proceed(original)
                }
            }
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(header)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        service = retrofit.create(RetrofitService::class.java)
    }

    private fun checkLogin(): Boolean {
        // 인증 토큰이 있는지 없는지로 로그인 여부 확인
        Log4k.e("checkLogin")
        val sp = getSharedPreferences("sp_login", Context.MODE_PRIVATE)
        val token = sp.getString("sp_loginn", "")
        return when {
            "" != token -> {
                true
            }
            else -> false
        }
    }

    private fun getUserToken(): String? {
        Log4k.e("getUserToken")
        val sp = getSharedPreferences("sp_login", Context.MODE_PRIVATE)
        val token = sp.getString("sp_loginn", "")
        return when {
            "" != token -> {
                token
            }
            else -> {
                ""
            }
        }
    }

}