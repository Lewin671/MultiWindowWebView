package com.example.webviewopenwindowtest

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class MultiWindowWebView : WebView {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {
        // Configure WebView settings
        settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            setSupportZoom(true)
            setSupportMultipleWindows(true)
            javaScriptCanOpenWindowsAutomatically = true
        }
    }
}