# MultiWindowWebView

MultiWindowWebView is a custom Android WebView implementation that enhances the standard WebView with built-in support for multiple windows and advanced JavaScript capabilities.

## Features

- **Multiple Window Support**: Native handling of window.open() JavaScript calls
- **Automatic Window Handling**: Automatic handling of user-initiated window opens like clicking on links

## Setup

1. Add the MultiWindowWebView to your layout XML:

```xml
<com.example.webviewopenwindowtest.MultiWindowWebView
    android:id="@+id/webview"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

2. Initialize in your Activity or Fragment:

```kotlin
val webView = findViewById<MultiWindowWebView>(R.id.webview)
```

## Usage

The MultiWindowWebView comes pre-configured with optimal settings for handling multiple windows and JavaScript content. To use it effectively, you need to implement custom WebViewClient and WebChromeClient classes.

### Implementing BaseWebViewClient

Extend BaseWebViewClient to handle URL loading and new window navigation:

```kotlin
class CustomWebViewClient(private val context: Context, forOpenNewWindow: Boolean) :
    BaseWebViewClient(forOpenNewWindow) {
    override fun openInNewActivity(url: String) {
        // Implement how new windows should be opened
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("url", url)
        }
        context.startActivity(intent)
    }
}
```

### Implementing BaseWebChromeClient

Extend BaseWebChromeClient to handle JavaScript window.open() requests:

```kotlin
class CustomWebChromeClient : BaseWebChromeClient() {
    override fun createTempWebViewClient(context: Context): BaseWebViewClient {
        return CustomWebViewClient(context, true)
    }
}
```

### Setting up in Activity

```kotlin
// Initialize WebView
val webView = findViewById<MultiWindowWebView>(R.id.webview)

// Set custom clients
webView.webViewClient = CustomWebViewClient(this, false)
webView.webChromeClient = CustomWebChromeClient()

// Load a URL
webView.loadUrl("https://your-website.com")
```

## Requirements

- Android SDK
- Kotlin support in your project
