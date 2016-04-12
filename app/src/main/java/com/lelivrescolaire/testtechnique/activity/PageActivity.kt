package com.lelivrescolaire.testtechnique.activity

import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Xml
import android.webkit.WebView
import android.webkit.WebViewClient
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

        object : AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg params: Void?): String {
                val content = StringBuilder()
                resources.assets.open("page.html").bufferedReader().forEachLine {
                    content.append(it.replace("TEXT_CONTENT", page!!.content)).appendln()
                }
                return content.toString()
            }

            override fun onPostExecute(result: String?) {
                webView.loadDataWithBaseURL("file:///android_asset/", result, "text/html", Xml.Encoding.UTF_8.name, null)
                webView.settings.allowFileAccessFromFileURLs = true
                webView.settings.javaScriptEnabled = true
                webView.setWebViewClient(object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        webView.evaluateJavascript("displayLineNumbers()", null);
                    }

                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        val uri = Uri.parse(url)

                        if (uri.scheme == "native") {
                            AlertDialog.Builder(this@PageActivity)
                                    .setTitle(R.string.from_javascript)
                                    .setMessage(uri.host)
                                    .setPositiveButton(android.R.string.ok, null)
                                    .show()
                            return true
                        } else {
                            return false
                        }
                    }
                })
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}