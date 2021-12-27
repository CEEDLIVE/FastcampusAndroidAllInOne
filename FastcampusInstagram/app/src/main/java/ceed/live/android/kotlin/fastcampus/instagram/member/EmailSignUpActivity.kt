package ceed.live.android.kotlin.fastcampus.instagram.member

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import ceed.live.android.kotlin.fastcampus.instagram.BaseActivity
import ceed.live.android.kotlin.fastcampus.instagram.MasterApplication
import ceed.live.android.kotlin.fastcampus.instagram.R
import ceed.live.android.kotlin.fastcampus.instagram.data.Register
import ceed.live.android.kotlin.fastcampus.instagram.data.User
import ceed.live.android.kotlin.fastcampus.instagram.logger.Log4k
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailSignUpActivity : BaseActivity() {

    lateinit var userNameView: EditText
    lateinit var userPassword1View: EditText
    lateinit var userPassword2View: EditText

    lateinit var btnRegister: Button
    lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_sign_up)

        initView(this@EmailSignUpActivity)
        setupListener()
    }

    private fun setupListener() {
        btnRegister.setOnClickListener {
            register()
        }
        btnLogin.setOnClickListener {
            login()
        }
    }

    private fun initView(activity: Activity) {
        userNameView = activity.findViewById(R.id.et_username)
        userPassword1View = activity.findViewById(R.id.et_password1)
        userPassword2View = activity.findViewById(R.id.et_password2)

        btnRegister = activity.findViewById(R.id.btn_register)
        btnLogin = activity.findViewById(R.id.btn_login)
    }

    private fun register() {
        val username = getUserName()
        val password1 = getUserPassword1()
        val password2 = getUserPassword2()

        Log4k.e("register username $username")
        Log4k.e("register password1 $password1")
        Log4k.e("register password2 $password2")

        if (checkRegister(username, password1, password2)) {
            val register = Register(username, password1, password2)

            registerFormUrlEncoded(username, password1, password2)
        }
    }

    private fun login() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun registerObject(register: Register) {
        (application as MasterApplication).service.registerObject(register).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log4k.e("onResponse response $response")
                val user = response.body()
                val token = user?.token
                token?.let {
                    saveUserToken(it, this@EmailSignUpActivity)
                    showToast("가입 성공")

                    (application as MasterApplication).createRetrofit()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log4k.e("onFailure call $call")
                Log4k.e("onFailure t $t")
                showToast("가입 실패")
            }
        })
    }

    private fun registerFormUrlEncoded(username: String, password1: String, password2: String) {
        (application as MasterApplication).service.registerFormUrlEncoded(username, password1, password2).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log4k.e("onResponse response $response")
                val user = response.body()
                val token = user?.token
                token?.let {
                    saveUserToken(it, this@EmailSignUpActivity)
                    showToast("가입 성공")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log4k.e("onFailure call $call")
                Log4k.e("onFailure t $t")
                showToast("가입 실패")
            }
        })
    }

    private fun checkRegister(username: String?, password1: String?, password2: String?) : Boolean {
        if (username == null) {
            showToast("유저이름 오류 : 필수값 이름 누락")
            return false
        }
        if (password1 == null) {
            showToast("비밀번호 오류 : 필수값 비밀번호1 누락")
            return false
        }
        if (password2 == null) {
            showToast("비밀번호 오류 : 필수값 비밀번호2 누락")
            return false
        }
        if (password1 != password2) {
            showToast("비밀번호 오류 : 비밀번호1, 비밀번호2 불일치")
            return false
        }
        return true
    }

    private fun saveUserToken(token: String, activity: Activity) {
        val sp = activity.getSharedPreferences("sp_login", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("sp_login", token)
        editor.apply()
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun getUserName(): String {
        return userNameView.text.toString()
    }

    private fun getUserPassword1(): String {
        return userPassword1View.text.toString()
    }

    private fun getUserPassword2(): String {
        return userPassword2View.text.toString()
    }
}