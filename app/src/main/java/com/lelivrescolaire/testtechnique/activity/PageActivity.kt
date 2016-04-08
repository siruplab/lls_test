package com.lelivrescolaire.testtechnique.activity

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Xml
import android.webkit.WebView
import android.widget.Toast
import com.lelivrescolaire.testtechnique.LlsApplication
import com.lelivrescolaire.testtechnique.R
import com.lelivrescolaire.testtechnique.model.Page

/**
 * Created by paour on 16/04/08.
 */
class PageActivity : AppCompatActivity() {
    val page: Page? by lazy { LlsApplication.pageIndex.get(intent.getIntExtra("id", 0)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (page == null) {
            Toast.makeText(this, R.string.page_not_found, Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val webView = WebView(this)
        title = page!!.title

        setContentView(webView)

        object : AsyncTask<Void,Void,String>() {
            override fun doInBackground(vararg params: Void?): String {
                val content = StringBuilder()
                resources.assets.open("page.html").bufferedReader().forEachLine {
                    content.append(it.replace("TEXT_CONTENT", page!!.content)).appendln()
                }
                return content.toString()
            }

            override fun onPostExecute(result: String?) {
                webView.loadData(result, "text/html", Xml.Encoding.UTF_8.name)
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}