package com.lelivrescolaire.testtechnique.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.lelivrescolaire.testtechnique.LlsApplication
import com.lelivrescolaire.testtechnique.R
import com.lelivrescolaire.testtechnique.model.Book
import com.lelivrescolaire.testtechnique.model.Page
import kotlinx.android.synthetic.main.activity_library.*

class BookActivity : AppCompatActivity() {
    val book: Book? by lazy { LlsApplication.bookIndex.get(intent.getIntExtra("id", 0)) }

    inner class PageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val titleView = (itemView as ViewGroup).findViewById(R.id.title) as TextView
        val coverView = (itemView as ViewGroup).findViewById(R.id.cover) as ImageView
        var page: Page? = null

        init {
            itemView.setOnClickListener(this)
        }

        fun bindPage(page: Page) {
            titleView.text = page.title
            this.page = page
            Glide
                    .with(this@BookActivity)
                    .load(page.cover)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .crossFade()
                    .into(coverView);
        }

        override fun onClick(v: View?) {
            startActivity(Intent(this@BookActivity, PageActivity::class.java)
                    .putExtra("id", page!!.id))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (book == null) {
            Toast.makeText(this, R.string.book_not_found, Toast.LENGTH_LONG).show()
            finish()
            return
        }

        setContentView(R.layout.activity_library)
        title = book!!.title

        val adapter: RecyclerView.Adapter<PageViewHolder> = object : RecyclerView.Adapter<PageViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PageViewHolder? {
                val itemView = layoutInflater.inflate(R.layout.page_item, parent, false)
                return PageViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: PageViewHolder?, position: Int) {
                holder!!.bindPage(book!!.pages[position])
            }

            override fun getItemCount(): Int {
                return book!!.pages.size
            }

            override fun getItemId(position: Int): Long {
                return book!!.pages[position].id.toLong()
            }
        }
        adapter.setHasStableIds(true)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this)
    }
}
