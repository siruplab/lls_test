package com.lelivrescolaire.testtechnique.activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.lelivrescolaire.testtechnique.R
import com.fasterxml.jackson.module.kotlin.readValue
import com.lelivrescolaire.testtechnique.LlsApplication
import com.lelivrescolaire.testtechnique.model.Book

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        object : AsyncTask<Void,Void,Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                resources.assets.open("books.json").use {
                    LlsApplication.Companion.addBooks(
                            jacksonObjectMapper().readValue<List<Book>>(it)
                    )
                }

                return null
            }

            override fun onPostExecute(result: Void?) {
                startActivity(Intent(this@SplashScreenActivity, LibraryActivity::class.java))
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}
