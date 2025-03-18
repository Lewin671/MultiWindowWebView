package com.example.webviewopenwindowtest

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Message
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class MultiWindowWebView : WebView {
    private var webChromeClientInstance: BaseWebChromeClient? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        setupWebView()
        setupWebChromeClient()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        // Configure WebView settings
        settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            setSupportZoom(true)
            setSupportMultipleWindows(true)
            javaScriptCanOpenWindowsAutomatically = true
        }

        // Set custom WebViewClient to handle page navigation
        webViewClient = BaseWebViewClient(context)
    }

    private fun setupWebChromeClient() {
        webChromeClientInstance = BaseWebChromeClient()
        webChromeClient = webChromeClientInstance
    }

    override fun destroy() {
        super.destroy()
        webChromeClientInstance?.destroy()
        webChromeClientInstance = null
    }
}