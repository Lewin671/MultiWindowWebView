package com.example.webviewopenwindowtest

import android.content.Context
import android.os.Message
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebView.WebViewTransport

abstract class BaseWebChromeClient : WebChromeClient() {

    override fun onCreateWindow(
        view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?
    ): Boolean {
        val context = view?.context ?: return false

        val tempWebView = WebView(context).apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                setSupportZoom(true)
                setSupportMultipleWindows(true)
                javaScriptCanOpenWindowsAutomatically = true
            }
        }
        tempWebView.webViewClient = createTempWebViewClient(tempWebView, context)


        resultMsg?.obj?.let { obj ->
            if (obj is WebViewTransport) {
                obj.webView = tempWebView
                resultMsg.sendToTarget()
            }
        }
        return true
    }


    protected abstract fun createTempWebViewClient(
        tempWebView: WebView,
        context: Context
    ): BaseWebViewClient
}