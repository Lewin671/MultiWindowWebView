package com.example.webviewopenwindowtest

import android.content.Context
import android.webkit.WebResourceRequest
import android.webkit.WebView

class DemoWebChromeClient : BaseWebChromeClient() {
    override fun createTempWebViewClient(
        tempWebView: WebView,
        context: Context
    ): BaseWebViewClient {
        return object : DemoWebViewClient(context, true) {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                tempWebView.destroy()
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }

}