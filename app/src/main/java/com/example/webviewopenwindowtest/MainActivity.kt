package com.example.webviewopenwindowtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: MultiWindowWebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)

        // Load URL from intent or default test HTML file
        val url = intent.getStringExtra("url") ?: "file:///android_asset/test.html"
        webView.loadUrl(url)
        webView.webViewClient = DemoWebViewClient(this, false)
        webView.webChromeClient = DemoWebChromeClient()
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }

    override fun onBackPressed() {
        // Handle back navigation in WebView
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}