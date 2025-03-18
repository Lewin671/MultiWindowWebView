package com.example.webviewopenwindowtest

import android.content.Context
import android.os.Message
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebView.WebViewTransport

open class BaseWebChromeClient : WebChromeClient() {
    private var tempWebView: WebView? = null

    override fun onCreateWindow(
        view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?
    ): Boolean {
        cleanupTempWebView()
        createAndSetupTempWebView(view?.context, resultMsg)
        return true
    }

    private fun cleanupTempWebView() {
        tempWebView?.destroy()
        tempWebView = null
    }

    private fun createAndSetupTempWebView(context: Context?, resultMsg: Message?) {
        if (context == null) return

        tempWebView = WebView(context).apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                setSupportZoom(true)
                setSupportMultipleWindows(true)
                javaScriptCanOpenWindowsAutomatically = true
            }
            webViewClient = createTempWebViewClient(context)
        }

        resultMsg?.obj?.let { obj ->
            if (obj is WebViewTransport) {
                obj.webView = tempWebView
                resultMsg.sendToTarget()
            }
        }
    }

    protected open fun createTempWebViewClient(context: Context): BaseWebViewClient {
        return BaseWebViewClient(context)
    }

    fun destroy() {
        cleanupTempWebView()
    }
}