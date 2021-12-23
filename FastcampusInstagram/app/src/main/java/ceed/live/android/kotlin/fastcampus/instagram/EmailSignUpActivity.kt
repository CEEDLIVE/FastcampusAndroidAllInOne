package ceed.live.android.kotlin.fastcampus.instagram

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class EmailSignUpActivity : BaseActivity() {

    lateinit var userNameView: EditText
    lateinit var userPassword1View: EditText
    lateinit var userPassword2View: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_sign_up)

        initView(this@EmailSignUpActivity)
    }

    private fun initView(activity: Activity) {
        userNameView = activity.findViewById(R.id.et_username)
        userPassword1View = activity.findViewById(R.id.et_password1)
        userPassword2View = activity.findViewById(R.id.et_password2)
    }

    fun getUserName(): String {
        return userNameView.text.toString()
    }

    fun getUserPassword1(): String {
        return userPassword1View.text.toString()
    }

    fun getUserPassword2(): String {
        return userPassword2View.text.toString()
    }
}