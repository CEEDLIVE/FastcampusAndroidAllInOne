package ceed.live.android.kotlin.fastcampus.instagram

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : BaseActivity() {

    lateinit var btnMoveNetwork: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView(this@MainActivity)
    }

    private fun initView(activity: Activity) {
        btnMoveNetwork = activity.findViewById(R.id.btn_move_network)
        btnMoveNetwork.setOnClickListener { moveActivity(it.id) }
    }

    private fun moveActivity(id: Int) {
        when (id) {
            R.id.btn_move_network -> {
                val intent = Intent(this, NetworkActivity::class.java)
                startActivity(intent)
            }
        }
    }
}