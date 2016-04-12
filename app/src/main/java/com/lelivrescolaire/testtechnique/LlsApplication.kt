package com.lelivrescolaire.testtechnique

import android.app.Application
import com.lelivrescolaire.testtechnique.model.Book

/**
 * Created by paour on 16/04/08.
 */
class LlsApplication : Application() {
    companion object {
        val bookList = mutableListOf<Book>()
        val bookIndex = mutableMapOf<Int, Book>()

        fun addBooks(newBooks : List<Book>) {
            bookList.clear()
            bookList.addAll(newBooks)

            bookIndex.clear()
            bookList.forEach { book ->
                bookIndex.put(book.id, book)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
    }
}