package ceed.live.android.kotlin.fastcampus.instagram.member

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ceed.live.android.kotlin.fastcampus.instagram.MasterApplication
import ceed.live.android.kotlin.fastcampus.instagram.R
import ceed.live.android.kotlin.fastcampus.instagram.data.User
import ceed.live.android.kotlin.fastcampus.instagram.logger.Log4k
import ceed.live.android.kotlin.fastcampus.instagram.post.OutstagramPostListActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var username: TextView
    lateinit var password: TextView
    lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView(this@LoginActivity)
    }

    private fun initView(activity: LoginActivity) {
        username = activity.findViewById(R.id.et_username)
        password = activity.findViewById(R.id.et_password)
        btnLogin = activity.findViewById(R.id.btn_login)
        btnLogin.setOnClickListener {
            val username = username.text.toString()
            val password = password.text.toString()
            (application as MasterApplication).service
                .login(username, password)
                .enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        val user = response.body()
                        val token = user?.token
                        token?.let {
                            saveUserToken(activity, token)
                            showToast("로그인 완료")
                            (application as MasterApplication).createRetrofit()
                            movePostListActivity()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {

                    }
            })
        }
    }

    private fun saveUserToken(activity: Activity, token: String) {
//        val sp = activity.getSharedPreferences("sp_login", Context.MODE_PRIVATE)
//        val editor = sp.edit()
//        editor.putString("sp_login", token)
//        editor.commit()

        Log4k.e("saveUserToken token: $token")

        MasterApplication.sharedPreferences.token = token
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    fun movePostListActivity() {
        val intent = Intent(this, OutstagramPostListActivity::class.java)
        startActivity(intent)
    }
}