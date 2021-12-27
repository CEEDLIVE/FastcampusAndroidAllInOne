package ceed.live.android.kotlin.fastcampus.instagram.http

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import ceed.live.android.kotlin.fastcampus.instagram.BaseActivity
import ceed.live.android.kotlin.fastcampus.instagram.Constants.Companion.API_STUDENTS_GET
import ceed.live.android.kotlin.fastcampus.instagram.logger.Log4k
import ceed.live.android.kotlin.fastcampus.instagram.R
import ceed.live.android.kotlin.fastcampus.instagram.adapter.PersonRecyclerViewAdapter
import ceed.live.android.kotlin.fastcampus.instagram.data.Person
import com.google.gson.Gson
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

class NetworkActivity : BaseActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var btnDoAsync: Button
    private lateinit var textView: TextView

    private var myVariable = 10
    private var list: MutableList<Person> = mutableListOf()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PersonRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)

        initView(this@NetworkActivity)
    }

    private fun initView(activity: Activity) {
        progressBar = activity.findViewById(R.id.progressBar)
        textView = activity.findViewById(R.id.textView)
        btnDoAsync = activity.findViewById(R.id.btnDoAsync)
        btnDoAsync.setOnClickListener {
            val task = NetworkTask(this)
            task.execute(10)
        }

        recyclerView = activity.findViewById(R.id.recyclerview)
    }

    private fun requestForHttpURLConnection(): String? {
        val url = URL(API_STUDENTS_GET)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

        connection.requestMethod = "GET"
        connection.setRequestProperty("Content-Type", "application/json")
        var buffer = ""
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            Log4k.d("responseForHttpURLConnection connection.inputStream: " + connection.inputStream)
            val reader = BufferedReader(
                InputStreamReader(
                    connection.inputStream,
                    "UTF-8"
                )
            )
            buffer = reader.readLine()
        }
        return buffer
    }

    fun parseJSONArray(buffer: String) {
        val jsonArray = JSONArray(buffer)
        (0 until jsonArray.length()).forEach {
            try {
                val v = jsonArray.getJSONObject(it)
                Log4k.e("v: $v")
            } catch(e: Throwable) {

            }
        }
    }

    private fun initRecyclerView(data: Array<Person>) {
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

//    fun JSONA.toMap(): Map<String, *> = keys().asSequence().associateWith {
//        when (val value = this[it])
//        {
//            is JSONArray ->
//            {
//                val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
//                JSONObject(map).toMap().values.toList()
//            }
//            is JSONObject -> value.toMap()
//            JSONObject.NULL -> null
//            else            -> value
//        }
//    }

    // Avoid memory leak
    companion object {
        class NetworkTask internal constructor(context: NetworkActivity) : AsyncTask<Int, String, String?>() {
            private var response: String? = null
            private val activityReference: WeakReference<NetworkActivity> = WeakReference(context)

            override fun onPreExecute() {
                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return
                activity.progressBar.visibility = View.VISIBLE
            }

            override fun doInBackground(vararg params: Int?): String? {
                publishProgress("Calls onProgressUpdate") // Calls onProgressUpdate()

                response = activityReference.get()?.requestForHttpURLConnection()
//                response = getTestThreadResponse(params[0])

                Log4k.d("doInBackground response: $response")

                return response
            }

            override fun onPostExecute(result: String?) {
                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return

                val data = Gson().fromJson(result, Array<Person>::class.java)
                val age = data[0].age

                Log4k.e("age: $age")

                activity.initRecyclerView(data)
                activity.progressBar.visibility = View.GONE

                Log4k.d("onPostExecute result: $result")

//                activity.textView.text = result
//                activity.myVariable = 100
            }

            override fun onProgressUpdate(vararg text: String?) {
                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return

                Toast.makeText(activity, text.firstOrNull(), Toast.LENGTH_SHORT).show()
            }

            fun getTestThreadResponse(seconds: Int?): String? {
                response = try {
                    val time = seconds?.times(1000)
                    time?.toLong()?.let { Thread.sleep(it / 2) }
                    publishProgress("Half Time") // Calls onProgressUpdate()
                    time?.toLong()?.let { Thread.sleep(it / 2) }
                    publishProgress("Sleeping Over") // Calls onProgressUpdate()
                    "Android was sleeping for $seconds seconds"
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                    e.message
                } catch (e: Exception) {
                    e.printStackTrace()
                    e.message
                }
                return response
            }
        }
    }
}