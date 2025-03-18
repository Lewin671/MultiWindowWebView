package com.example.webviewopenwindowtest

import android.content.Context
import android.content.Intent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

open class BaseWebViewClient(private val context: Context) : WebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        request?.url?.toString()?.let { url ->
            openInNewActivity(url)
        }
        return true
    }

    protected open fun openInNewActivity(url: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("url", url)
        }
        context.startActivity(intent)
    }
}