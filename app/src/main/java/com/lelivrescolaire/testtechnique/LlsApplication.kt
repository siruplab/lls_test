package com.lelivrescolaire.testtechnique

import android.app.Application
import com.lelivrescolaire.testtechnique.model.Book

/**
 * Created by paour on 16/04/08.
 */
class LlsApplication : Application() {
    companion object {
        val books = mutableListOf<Book>()

        fun addBooks(newBooks : List<Book>) {
            books.addAll(newBooks)
        }
    }

    override fun onCreate() {
        super.onCreate()
    }
}