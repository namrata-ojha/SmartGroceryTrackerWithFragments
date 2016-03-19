package com.scu.smartgrocerytracker.recipe;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.scu.smartgrocerytracker.MapView;
import com.scu.smartgrocerytracker.R;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by namrataojha on 2/18/16.
 */
public class Recipe extends Fragment {
    private WebView mWebview ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.recipe_view, container, false);

        mWebview = (WebView)rootView.findViewById(R.id.webView);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = getActivity();

        mWebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        });




        mWebview .loadUrl("http://www.epicurious.com");
       // setContentView(mWebview );


        return rootView;
    }
}
