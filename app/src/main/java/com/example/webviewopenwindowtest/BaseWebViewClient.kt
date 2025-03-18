package com.example.webviewopenwindowtest

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

abstract class BaseWebViewClient(private val forOpenNewWindow: Boolean = false) : WebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        if (forOpenNewWindow) {
            request?.url?.toString()?.also {
                openInNewActivity(it)
            }
        }
        return false
    }

    protected abstract fun openInNewActivity(url: String)
}