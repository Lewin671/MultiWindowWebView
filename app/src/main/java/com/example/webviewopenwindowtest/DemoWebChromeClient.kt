package com.example.webviewopenwindowtest

import android.content.Context

class DemoWebChromeClient : BaseWebChromeClient() {
    override fun createTempWebViewClient(context: Context): BaseWebViewClient {
        return DemoWebViewClient(context, true)
    }
}