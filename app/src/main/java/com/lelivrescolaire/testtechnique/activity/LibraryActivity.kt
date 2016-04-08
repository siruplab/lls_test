package com.lelivrescolaire.testtechnique.activity

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
import kotlinx.android.synthetic.main.activity_library.*

class LibraryActivity : AppCompatActivity() {
    class BookViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val titleView = (itemView as ViewGroup).findViewById(R.id.title) as TextView
        val coverView = (itemView as ViewGroup).findViewById(R.id.cover) as ImageView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        val adapter: RecyclerView.Adapter<BookViewHolder> = object : RecyclerView.Adapter<BookViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BookViewHolder? {
                return BookViewHolder(layoutInflater.inflate(R.layout.library_item, parent, false))
            }

            override fun onBindViewHolder(holder: BookViewHolder?, position: Int) {
                val book = LlsApplication.books[position]
                holder!!.titleView.text = book.title
                Glide
                        .with(this@LibraryActivity)
                        .load(book.cover)
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .crossFade()
                        .into(holder.coverView);
            }

            override fun getItemCount(): Int {
                return LlsApplication.books.size
            }

            override fun getItemId(position: Int): Long {
                return LlsApplication.books[position].id.toLong()
            }
        }
        adapter.setHasStableIds(true)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = GridLayoutManager(this, 2)
    }
}
