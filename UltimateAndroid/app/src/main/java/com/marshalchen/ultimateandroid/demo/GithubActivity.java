package com.marshalchen.ultimateandroid.demo;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;




public class GithubActivity extends AppCompatActivity {
 //"'https://api.github.com/user/repos?page=2&per_page=100'"
    String baseUrl="https://api.github.com/search/repositories?q=tetris+language:assembly&sort=stars&order=desc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
