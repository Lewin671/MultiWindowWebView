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
    private var tempWebView: WebView? = null

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
        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }
    }

    private fun setupWebChromeClient() {
        webChromeClient = object : WebChromeClient() {
            override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message?
            ): Boolean {
                cleanupTempWebView()
                createAndSetupTempWebView(resultMsg)
                return true
            }
        }
    }

    private fun cleanupTempWebView() {
        tempWebView?.destroy()
        tempWebView = null
    }

    private fun createAndSetupTempWebView(resultMsg: Message?) {
        tempWebView = WebView(context).apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                setSupportZoom(true)
                setSupportMultipleWindows(true)
                javaScriptCanOpenWindowsAutomatically = true
            }
            webViewClient = createTempWebViewClient()
        }

        // Set up message transport
        resultMsg?.obj?.let { obj ->
            if (obj is WebViewTransport) {
                obj.webView = tempWebView
                resultMsg.sendToTarget()
            }
        }
    }

    private fun createTempWebViewClient() = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            request?.url?.toString()?.let { url ->
                openInNewActivity(url)
                cleanupTempWebView()
            }
            return true
        }
    }

    private fun openInNewActivity(url: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("url", url)
        }
        context.startActivity(intent)
    }

    override fun destroy() {
        super.destroy()
        tempWebView?.destroy()
        tempWebView = null
    }
}