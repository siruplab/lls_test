package com.lelivrescolaire.testtechnique

import android.app.Application
import com.lelivrescolaire.testtechnique.model.Book
import com.lelivrescolaire.testtechnique.model.Page

/**
 * Created by paour on 16/04/08.
 */
class LlsApplication : Application() {
    companion object {
        val bookList = mutableListOf<Book>()
        val bookIndex = mutableMapOf<Int, Book>()
        val pageIndex = mutableMapOf<Int, Page>()

        fun addBooks(newBooks : List<Book>) {
            bookList.clear()
            bookList.addAll(newBooks)

            bookIndex.clear()
            pageIndex.clear()
            bookList.forEach {
                bookIndex.put(it.id, it)
                it.pages.forEach { pageIndex.put(it.id, it) }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
    }
}