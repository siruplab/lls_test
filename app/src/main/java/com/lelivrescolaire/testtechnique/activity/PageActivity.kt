package com.lelivrescolaire.testtechnique.activity

import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.annotation.Keep
import android.support.annotation.MainThread
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Xml
import android.webkit.JavascriptInterface
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
    val page: Page? by lazy {
        LlsApplication.bookIndex
                .get(intent.getIntExtra("book_id", 0))
                ?.pageIndex?.get(intent.getIntExtra("page_id", 0))
    }
    private val webView: WebView by lazy { WebView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (page == null) {
            Toast.makeText(this, R.string.page_not_found, Toast.LENGTH_LONG).show()
            finish()
            return
        }

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
                setupWebView(result)
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    @MainThread
    fun setupWebView(result: String?) {
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
                    displayDialog(uri.host)
                    return true
                } else {
                    return false
                }
            }

        })
        webView.addJavascriptInterface(object {
            // we could use the PageActivity object directly, but on old versions of Android, all
            // methods were exposed, not just the ones annotated with @JavascriptInterface
            @Suppress("unused")
            @JavascriptInterface
            @Keep
            fun displayDialog(message: String) {
                this@PageActivity.displayDialog(message)
            }
        }, "androidInterface")
    }

    private fun displayDialog(message: String) {
        AlertDialog.Builder(this@PageActivity)
                .setTitle(R.string.from_javascript)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show()
    }
}