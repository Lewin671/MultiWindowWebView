package com.example.webviewopenwindowtest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private var tempWebView: WebView? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        setupMainWebView()
        setupWebChromeClient()

        // Load URL from intent or default test HTML file
        val url = intent.getStringExtra("url") ?: "file:///android_asset/test.html"
        webView.loadUrl(url)
    }

    private fun configureWebView(webView: WebView) {
        // Configure WebView settings
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            setSupportZoom(true)
            setSupportMultipleWindows(true)
            javaScriptCanOpenWindowsAutomatically = true
        }
    }

    private fun setupMainWebView() {
        configureWebView(webView)

        // Set custom WebViewClient to handle page navigation and _blank links
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }
    }

    private fun setupWebChromeClient() {
        webView.webChromeClient = object : WebChromeClient() {
            override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: android.os.Message?
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

    private fun createAndSetupTempWebView(resultMsg: android.os.Message?) {
        tempWebView = WebView(this).apply {
            configureWebView(this)
            webViewClient = createTempWebViewClient()
        }

        // Set up message transport
        resultMsg?.obj?.let { obj ->
            if (obj is WebView.WebViewTransport) {
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

    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
        tempWebView?.destroy()
        tempWebView = null
    }

    override fun onBackPressed() {
        // Handle back navigation in WebView
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private fun openInNewActivity(url: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("url", url)
        }
        startActivity(intent)
    }
}