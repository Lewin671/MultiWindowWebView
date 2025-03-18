package com.example.webviewopenwindowtest

import android.content.Context
import android.content.Intent

class DemoWebViewClient(private val context: Context, forOpenNewWindow: Boolean) :
    BaseWebViewClient(forOpenNewWindow) {
    override fun openInNewActivity(url: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("url", url)
        }
        context.startActivity(intent)
    }
}