package info.androidhive.materialtabs.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import info.androidhive.materialtabs.R;

public class Oplata extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oplata);

        WebView webView = (WebView) findViewById (R.id.opl);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://oplata.qiwi.com/form?invoiceUid=91ea0832-dcb0-4e67-8b68-b6bbbad11722");
    }
}