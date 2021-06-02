package com.sro.spacex;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WikipediaActivity extends Activity {
    ProgressBar bar;
    WebView webView;
    ImageView back;
    TextView urlText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wiki);
        webView = findViewById(R.id.webview);
        bar = findViewById(R.id.progressbar);
        back = findViewById(R.id.back);
        urlText = findViewById(R.id.link_text);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        urlText.setText(url);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);


        webView.getSettings().setDisplayZoomControls(true);
        webView.loadUrl(url);
    }
}