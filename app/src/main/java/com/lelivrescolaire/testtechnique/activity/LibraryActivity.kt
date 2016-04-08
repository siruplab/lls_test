package com.lelivrescolaire.testtechnique.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lelivrescolaire.testtechnique.LlsApplication

import com.lelivrescolaire.testtechnique.R
import com.lelivrescolaire.testtechnique.model.Book
import kotlinx.android.synthetic.main.activity_library.*

class LibraryActivity : AppCompatActivity() {

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val titleView = (itemView as ViewGroup).findViewById(R.id.title) as TextView
        val coverView = (itemView as ViewGroup).findViewById(R.id.cover) as ImageView
        var book: Book? = null

        init {
            itemView.setOnClickListener(this)
        }

        fun bindBook(book: Book) {
            titleView.text = book.title
            this.book = book
            Glide
                    .with(this@LibraryActivity)
                    .load(book.cover)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .crossFade()
                    .into(coverView);
        }

        override fun onClick(v: View?) {
            startActivity(Intent(this@LibraryActivity, BookActivity::class.java)
                    .putExtra("id", book!!.id))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        val adapter: RecyclerView.Adapter<BookViewHolder> = object : RecyclerView.Adapter<BookViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BookViewHolder? {
                val itemView = layoutInflater.inflate(R.layout.book_item, parent, false)
                return BookViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: BookViewHolder?, position: Int) {
                holder!!.bindBook(LlsApplication.bookList[position])
            }

            override fun getItemCount(): Int {
                return LlsApplication.bookList.size
            }

            override fun getItemId(position: Int): Long {
                return LlsApplication.bookList[position].id.toLong()
            }
        }
        adapter.setHasStableIds(true)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = GridLayoutManager(this, 2)
    }
}
