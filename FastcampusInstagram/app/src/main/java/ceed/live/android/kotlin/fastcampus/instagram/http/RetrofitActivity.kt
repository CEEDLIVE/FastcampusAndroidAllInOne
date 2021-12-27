package ceed.live.android.kotlin.fastcampus.instagram.http

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import ceed.live.android.kotlin.fastcampus.instagram.BaseActivity
import ceed.live.android.kotlin.fastcampus.instagram.logger.Log4k
import ceed.live.android.kotlin.fastcampus.instagram.R
import ceed.live.android.kotlin.fastcampus.instagram.adapter.PersonRecyclerViewAdapter
import ceed.live.android.kotlin.fastcampus.instagram.data.Person
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitActivity : BaseActivity() {

    private var list: MutableList<Person> = mutableListOf()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PersonRecyclerViewAdapter

    private lateinit var btnCreateStudent: Button

    private lateinit var service: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        initView(this@RetrofitActivity)
        initRetrofit()
    }

    private fun initView(activity: Activity) {
        recyclerView = activity.findViewById(R.id.recyclerview)
        btnCreateStudent = activity.findViewById(R.id.btn_create_student)
        btnCreateStudent.setOnClickListener {
//            createStudentHashMap(service)
            createStudentEntity(service)
        }
    }

    private fun initRetrofit() {
        // http://mellowcode.org/json/students/
        // http://mellowcode.org/test/code/

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(RetrofitService::class.java)

        readStudentList(service)
    }

    private fun readStudentList(service: RetrofitService) {
        service.getStudentList().enqueue(object: Callback<ArrayList<Person>> {
            override fun onResponse(
                call: Call<ArrayList<Person>>,
                response: Response<ArrayList<Person>>
            ) {
                if (response.isSuccessful) {
                    val personList = response.body()
                    val code = response.code()
                    val header = response.headers()

                    Log4k.e("personList: $personList")
                    Log4k.e("code: $code")
                    Log4k.e("header: $header")

                    personList?.let { initRecyclerView(it) }
                }
            }

            override fun onFailure(call: Call<ArrayList<Person>>, t: Throwable) {
                Log4k.e("call: " + call)
                Log4k.e("t: " + t)
            }
        })
    }

    private fun createStudentHashMap(service: RetrofitService) {
        Log4k.e("createStudentHashMap")
        val params = HashMap<String, Any>()
        params.put("name", "씨앗")
        params.put("age", 37)
        params.put("intro", "안녕안녕")
        service.createStudentHashMap(params).enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (response.isSuccessful) {
                    val person = response.body()
                    Log4k.e("person: ${person?.name}")
                }
            }
            override fun onFailure(call: Call<Person>, t: Throwable) {
                Log4k.e("createStudent Person $call")
                Log4k.e("createStudent Throwable $t")
            }
        })
    }

    private fun createStudentEntity(service: RetrofitService) {
        Log4k.e("createStudentEntity")
        val person = Person(name = "이지보이", age = 37, intro = "안녕하세요. 이지보이 입니다.")
        service.createStudentEntity(person).enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (response.isSuccessful) {
                    val person = response.body()
                    Log4k.e("person: ${person?.name}")
                }
            }
            override fun onFailure(call: Call<Person>, t: Throwable) {
                Log4k.e("createStudent Person $call")
                Log4k.e("createStudent Throwable $t")
            }
        })
    }

    private fun initRecyclerView(data: ArrayList<Person>) {
        data.forEach {
            list.add(it)
        }

        adapter = PersonRecyclerViewAdapter(list)
        adapter.setOnItemDetailClickListener(object : PersonRecyclerViewAdapter.OnItemDetailClickListener {
            override fun onClick(data: Person, position: Int) {
                readItem(data)
            }
        })
        adapter.setOnItemDeleteClickListener(object : PersonRecyclerViewAdapter.OnItemDeleteClickListener {
            override fun onClick(data: Person, position: Int) {
                deleteItem(data)
            }
        })

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }

    private fun readItem(data: Person) {
//        val intent = Intent(this, DetailActivity::class.java)
//        intent.putExtra("data", data)
//        startActivity(intent)
        Log4k.i("readItem $data")
    }

    private fun deleteItem(data: Person) {
        list.remove(data)
        adapter.notifyDataSetChanged()

        Log4k.i("deleteItem $data")
    }
}