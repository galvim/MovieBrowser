package com.example.rent.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;

import java.io.IOException;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusAppCompatActivity<ListingPresenter> {

    private static final String SEARCH_TITLE = "search_title";
    private MoviesListAdapter adapter;
    private ViewFlipper viewFlipper;
    private TextView noInternet;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        String title = getIntent().getStringExtra(SEARCH_TITLE);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new MoviesListAdapter();
        recyclerView.setAdapter(adapter);
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        noInternet = (TextView) findViewById(R.id.text_no_internet);

        getPresenter().getDataAsync(title);
    }

    public void setDataOnUiThread(SearchResult result, boolean isProblemWithInternet) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(isProblemWithInternet) {
                    viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInternet));
                } else {
                    viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(recyclerView));
                    adapter.setItems(result.getItems());
                }
            }
        });
    }


    public static Intent createIntent(Context context, String title) {
        Intent intent = new Intent(context, ListingActivity.class);
        intent.putExtra(SEARCH_TITLE, title);
        return intent;

    }


}
