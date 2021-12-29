package ceed.live.android.kotlin.fastcampus.instagram

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import ceed.live.android.kotlin.fastcampus.instagram.http.RetrofitService
import ceed.live.android.kotlin.fastcampus.instagram.logger.Log4k
import ceed.live.android.kotlin.fastcampus.instagram.sharedpreferences.MySharedPreferences
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MasterApplication : Application() {

    lateinit var service: RetrofitService

    companion object {
        lateinit var sharedPreferences: MySharedPreferences
    }

    override fun onCreate() {
        Log4k.e("onCreate")

        sharedPreferences = MySharedPreferences(applicationContext)

        super.onCreate()

        Stetho.initializeWithDefaults(this)
        createRetrofit()
    }

    fun createRetrofit() {
        val header = Interceptor {
            val original = it.request()

            val token = getUserToken()
            Log4k.e("createRetrofit token: $token")

            when {
                checkLogin() -> {
                    val token = getUserToken()
                    token?.let { authorization ->

                        Log4k.e("createRetrofit if authorization: $authorization")

                        val request = original.newBuilder()
                            .header("Authorization", "token $authorization")
                            .build()
                        it.proceed(request)
                    }
                }
                else -> {

                    Log4k.e("createRetrofit else")

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

    fun checkLogin(): Boolean {
        // 인증 토큰이 있는지 없는지로 로그인 여부 확인
        Log4k.e("checkLogin")
//        val sp = getSharedPreferences("sp_login", Context.MODE_PRIVATE)
//        val token = sp.getString("sp_login", "")
        val token = sharedPreferences.token

        Log4k.e("checkLogin token: $token")

        return when {
            "" != token -> {
                true
            }
            else -> false
        }
    }

    private fun getUserToken(): String? {
        Log4k.e("getUserToken")
//        val sp = getSharedPreferences("sp_login", Context.MODE_PRIVATE)
//        val token = sp.getString("sp_login", "")
        val token = sharedPreferences.token

        Log4k.e("getUserToken token: $token")

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