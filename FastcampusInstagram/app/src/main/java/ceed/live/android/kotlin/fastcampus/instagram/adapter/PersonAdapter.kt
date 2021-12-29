package ceed.live.android.kotlin.fastcampus.instagram.adapter

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ceed.live.android.kotlin.fastcampus.instagram.logger.Log4k
import ceed.live.android.kotlin.fastcampus.instagram.R
import ceed.live.android.kotlin.fastcampus.instagram.data.Person


class PersonAdapter(private val dataSet: MutableList<Person>) :
    RecyclerView.Adapter<PersonAdapter.ViewHolder>(), Adapter {

    // Custom Listener
    interface OnItemDetailClickListener {
        fun onClick(data: Person, position: Int)
    }

    // Custom Listener
    interface OnItemDeleteClickListener {
        fun onClick(data: Person, position: Int)
    }

    // 클릭리스너 선언
    private lateinit var onItemDetailClickListener: OnItemDetailClickListener
    private lateinit var onItemDeleteClickListener: OnItemDeleteClickListener

    // 클릭리스너 등록 매소드
    fun setOnItemDetailClickListener(itemClickListener: OnItemDetailClickListener) {
        this.onItemDetailClickListener = itemClickListener
    }

    fun setOnItemDeleteClickListener(itemClickListener: OnItemDeleteClickListener) {
        this.onItemDeleteClickListener = itemClickListener
    }

    // inner 라는 예약어를 사용해서 중첩 클래스를 나타내면 중첩 클래스의 바깥 클래스가 가지는 멤버에 접근할 수 있다.

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val personIdView: TextView = view.findViewById(R.id.data_person_id)
        private val personNameView: TextView = view.findViewById(R.id.data_person_name)
        private val personAgeView: TextView = view.findViewById(R.id.data_person_age)
        private val personIntroView: TextView = view.findViewById(R.id.data_person_intro)

        init {
            // Define click listener for the ViewHolder's View.
            Log4k.e("ViewHolder init")

        }

        fun bind(data: Person, position: Int) {

            // set data
            personIdView.text = data.id.toString()
            personNameView.text = data.name
            personAgeView.text = data.age.toString()
            personIntroView.text = data.intro

//            if (data.image != null) {
//                imageView.setImageResource(data.image)
//            } else {
//                imageView.setImageResource(R.drawable.ic_baseline_delete_forever_24)
//            }

            // set event
            if (adapterPosition != RecyclerView.NO_POSITION) {
                personIdView.setOnClickListener { onItemDeleteClickListener.onClick(data, position) }
                personIntroView.setOnClickListener { onItemDetailClickListener.onClick(data, position) }
            }
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_person, viewGroup, false)

        Log4k.e("onCreateViewHolder")

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.bind(dataSet[position], position)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    // Todo
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
