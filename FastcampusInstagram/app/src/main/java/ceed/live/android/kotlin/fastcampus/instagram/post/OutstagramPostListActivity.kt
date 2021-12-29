package ceed.live.android.kotlin.fastcampus.instagram.post

import android.app.Activity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import ceed.live.android.kotlin.fastcampus.instagram.BaseActivity
import ceed.live.android.kotlin.fastcampus.instagram.MasterApplication
import ceed.live.android.kotlin.fastcampus.instagram.R
import ceed.live.android.kotlin.fastcampus.instagram.adapter.PostAdapter
import ceed.live.android.kotlin.fastcampus.instagram.data.Person
import ceed.live.android.kotlin.fastcampus.instagram.data.Post
import ceed.live.android.kotlin.fastcampus.instagram.http.RetrofitService
import ceed.live.android.kotlin.fastcampus.instagram.logger.Log4k
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutstagramPostListActivity : BaseActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    private var list: MutableList<Post> = mutableListOf()

    lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outstagram_post_list)

        initView(this@OutstagramPostListActivity)

        getInstagramPostAll((application as MasterApplication).service)
    }

    private fun initView(activity: Activity) {
        progressBar = activity.findViewById(R.id.progressBar)

        recyclerView = activity.findViewById(R.id.rv_list_post)
    }

    private fun initRecyclerView(data: ArrayList<Post>) {
        data.forEach {
            list.add(it)
        }
        adapter = PostAdapter(list)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }

    private fun getInstagramPostAll(service: RetrofitService) {
        service.getInstagramPostAll().enqueue(object: Callback<ArrayList<Post>> {
            override fun onResponse(
                call: Call<ArrayList<Post>>,
                response: Response<ArrayList<Post>>
            ) {
                if (response.isSuccessful) {
                    val postList = response.body()
                    val code = response.code()
                    val header = response.headers()

                    Log4k.e("postList: $postList")
                    Log4k.e("code: $code")
                    Log4k.e("header: $header")

                    postList?.let { initRecyclerView(it) }
                }
            }

            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                Log4k.e("call: " + call)
                Log4k.e("t: " + t)
            }
        })
    }
}