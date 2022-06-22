package com.utn.nerdypedia.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.utn.nerdypedia.R
import com.utn.nerdypedia.entities.Session
import com.utn.nerdypedia.viewmodels.BiographyViewModel

class BiographyFragment : Fragment() {

    companion object {
        fun newInstance() = BiographyFragment()
    }

    private lateinit var viewModel: BiographyViewModel
    private lateinit var v: View
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.biography_fragment, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BiographyViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        webView = v.findViewById(R.id.webView)
        progressBar = v.findViewById(R.id.progressBar)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webView.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }
        }

        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
//        webView.loadUrl(Session.scientist.biographyUrl)
        Session.scientist?.let { webView.loadUrl(it.biographyUrl) }
    }

/*    // if you press Back button this code will work
    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (webView.canGoBack())
            webView.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }*/
}