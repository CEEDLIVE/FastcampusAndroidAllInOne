package ceed.live.android.kotlin.fastcampus.instagram

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import ceed.live.android.kotlin.fastcampus.instagram.http.NetworkActivity
import ceed.live.android.kotlin.fastcampus.instagram.http.RetrofitActivity
import ceed.live.android.kotlin.fastcampus.instagram.member.EmailSignUpActivity
import ceed.live.android.kotlin.fastcampus.instagram.permisson.PermissionActivity
import java.util.*


class MainActivity : BaseActivity() {

//    lateinit var btnMoveNetwork: Button
//    lateinit var btnMoveRetrofit: Button

    private lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayout = findViewById(R.id.layout_chapter_list)

//        initView(this@MainActivity)

        initDynamicChapterButtons()
    }

//    private fun initView(activity: Activity) {
//        btnMoveNetwork = activity.findViewById(R.id.btn_move_network)
//        btnMoveNetwork.setOnClickListener { moveActivity(it.id) }
//
//        btnMoveNetwork = activity.findViewById(R.id.btn_move_retrofit)
//        btnMoveNetwork.setOnClickListener { moveActivity(it.id) }
//    }

    private fun initDynamicChapterButtons() {
        val chapterList = resources.getStringArray(R.array.chapterList)
        for ((index, value) in chapterList.withIndex()) {
            val button = Button(this)
            button.id = index
            button.text = value.uppercase(Locale.getDefault())
            button.setOnClickListener {
                moveActivity(button.text.toString())
            }
            linearLayout.addView(button)
        }
    }

    private fun moveActivity(id: String) {
        when (id) {
            "NETWORK" -> {
                val intent = Intent(this, NetworkActivity::class.java)
                startActivity(intent)
            }
            "RETROFIT" -> {
                val intent = Intent(this, RetrofitActivity::class.java)
                startActivity(intent)
            }
            "PERMISSION" -> {
                val intent = Intent(this, PermissionActivity::class.java)
                startActivity(intent)
            }
            "MEMBER" -> {
                val intent = Intent(this, EmailSignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
