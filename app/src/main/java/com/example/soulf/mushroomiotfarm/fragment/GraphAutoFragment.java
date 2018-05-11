package com.example.soulf.mushroomiotfarm.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.soulf.mushroomiotfarm.MainActivity;
import com.example.soulf.mushroomiotfarm.R;
import com.google.firebase.auth.FirebaseAuth;

public class GraphAutoFragment extends Fragment {

    private String urlField1 = "https://thingspeak.com/channels/437884/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=Humidity+%26Temp&type=line";
    private String urlField2 = "https://thingspeak.com/channels/437884/charts/3?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=FanSw&type=line";
    private String urlField3 = "https://thingspeak.com/channels/437884/charts/2?average=10&bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=Light&type=line";
    private String urlField4 = "https://thingspeak.com/channels/437884/charts/5?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=CCTVSw&type=line";
    private String urlField5 = "https://thingspeak.com/channels/437884/charts/4?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=CloudSw&type=line";


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Create Toolbar
        createToolbar();
        // Create WebView
        createWebView();

    }//Main method



    private void createWebView() {

        WebView field1WebView = getView().findViewById(R.id.webViewTemp);
        WebView field2WebView = getView().findViewById(R.id.webViewFan);
        WebView field3WebView = getView().findViewById(R.id.webViewlight);
        WebView field4WebView = getView().findViewById(R.id.webViewCCTV);
        WebView field5WebView = getView().findViewById(R.id.webViewCloud);
//        WebView field5WebView = getView().findViewById(R.id.webViewHumidity);


        WebViewClient field1WebViewClient = new WebViewClient();
        field1WebView.setWebViewClient(field1WebViewClient);
        field1WebView.loadUrl(urlField1);
        field1WebView.getSettings().setJavaScriptEnabled(true);

        WebViewClient field2WebViewClient = new WebViewClient();
        field2WebView.setWebViewClient(field2WebViewClient);
        field2WebView.loadUrl(urlField2);
        field2WebView.getSettings().setJavaScriptEnabled(true);
//
        WebViewClient field3WebViewClient = new WebViewClient();
        field3WebView.setWebViewClient(field3WebViewClient);
        field3WebView.loadUrl(urlField3);
        field3WebView.getSettings().setJavaScriptEnabled(true);
//
        WebViewClient field4WebViewClient = new WebViewClient();
        field4WebView.setWebViewClient(field4WebViewClient);
        field4WebView.loadUrl(urlField4);
        field4WebView.getSettings().setJavaScriptEnabled(true);
//
        WebViewClient field5WebViewClient = new WebViewClient();
        field5WebView.setWebViewClient(field5WebViewClient);
        field5WebView.loadUrl(urlField5);
        field5WebView.getSettings().setJavaScriptEnabled(true);
    } //createWebView

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_graph, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//signout
        if (item.getItemId() == R.id.itemSignOut){
            mySignOut();
        }

        return super.onOptionsItemSelected(item);
    }

    private void mySignOut() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

//        Return to AuthenFragment
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentMainFragment, new AuthenFragment())
                .commit();
    }





    private void createToolbar() {

        Toolbar toolbar = getView().findViewById(R.id.toolbarManual);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.temp_th));
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.update_th));

        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).getSupportFragmentManager().popBackStack();
            }
        });
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manual, container, false);
        return view;
    }
}
