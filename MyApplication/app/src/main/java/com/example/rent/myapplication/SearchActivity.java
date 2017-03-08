package com.example.rent.myapplication;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SearchActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        TextInputEditText edit_text = (TextInputEditText) findViewById(R.id.text_input_edit);
        ImageButton search_button = (ImageButton) findViewById(R.id.search_button);
        search_button.setOnClickListener(v -> {
            startActivity(ListingActivity.createIntent(SearchActivity.this,edit_text.getText().toString()));
        });

    }


}
