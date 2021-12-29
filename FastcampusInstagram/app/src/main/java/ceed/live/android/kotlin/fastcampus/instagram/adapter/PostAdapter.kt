package ceed.live.android.kotlin.fastcampus.instagram.adapter

import android.database.DataSetObserver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ceed.live.android.kotlin.fastcampus.instagram.R
import ceed.live.android.kotlin.fastcampus.instagram.coroutine.ImageLoader
import ceed.live.android.kotlin.fastcampus.instagram.data.Post
import ceed.live.android.kotlin.fastcampus.instagram.logger.Log4k
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL


class PostAdapter(private val dataSet: MutableList<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>(), Adapter {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var postImageView: ImageView = view.findViewById(R.id.iv_post_image)
        private var postOwnerView: TextView = view.findViewById(R.id.tv_post_owner)
        private var postContentView: TextView = view.findViewById(R.id.tv_post_content)

        init {
            postImageView = view.findViewById(R.id.iv_post_image)
            postOwnerView = view.findViewById(R.id.tv_post_owner)
            postContentView = view.findViewById(R.id.tv_post_content)
        }

        fun bind(data: Post, position: Int) {

            // set view via data
            postOwnerView.text = data.owner
            postContentView.text = data.content
            when {
                data.image != null -> {
                    // android.os.NetworkOnMainThreadException
//                    readImageFromInputStream(data.image, postImageView)

                    // Coroutine
//                    readImageFromCoroutine(data.image, postImageView)

                    // Glide
                    readImageFromGlide(data.image, postImageView)
                }
                else -> {
                    postImageView.setImageResource(R.drawable.ic_launcher_foreground)
                }
            }

            // set event
        }
    }

    fun readImageFromInputStream(imageUrl: String, imageView: ImageView) {
        val inputStream: InputStream = URL(imageUrl).openStream()
        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
        imageView.setImageBitmap(bitmap)
    }

    fun readImageFromCoroutine(imageUrl: String, imageView: ImageView) {

        Log4k.i("    readImageFromCoroutine imageUrl: $imageUrl")

        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = withContext(Dispatchers.IO) {
                ImageLoader.loadImage(imageUrl)
            }
            imageView.setImageBitmap(bitmap)
        }
    }

    fun readImageFromGlide(imageUrl: String, imageView: ImageView) {

        Log4k.i("    readImageFromGlide imageUrl: $imageUrl")

        Glide
            .with(imageView.context)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.placeholder_hello_world)
            .into(imageView);
    }

    fun readImageFromPicasso() {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position], position)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun registerDataSetObserver(p0: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterDataSetObserver(p0: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getViewTypeCount(): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }
}