package com.example.rent.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

import static io.reactivex.android.schedulers.AndroidSchedulers.*;
import static io.reactivex.schedulers.Schedulers.io;

@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusAppCompatActivity<ListingPresenter> {

    private static final String SEARCH_TITLE = "search_title";
    private static final String SEARCH_YEAR = "search_year";
    private static final String SEARCH_TYPE = "search_type";
    public static final int NO_YEAR_SELECTED = -1;
    private MoviesListAdapter adapter;
    //butterknife nie dziala na polach prywatnych
    @BindView(R.id.view_flipper)
     ViewFlipper viewFlipper;
    @BindView(R.id.text_no_internet)
    TextView noInternet;
    @BindView(R.id.recycler_view)
   RecyclerView recyclerView;

    @BindView(R.id.results_imageView)
    ImageView no_results;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        String title = getIntent().getStringExtra(SEARCH_TITLE);
        int year = getIntent().getIntExtra(SEARCH_YEAR, NO_YEAR_SELECTED);
        String type = getIntent().getStringExtra(SEARCH_TYPE);

        //int year = getIntent().getIntExtra(year,SEARCH_TITLE)
        // zamiast find do widokow
        ButterKnife.bind(this);

        //recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new MoviesListAdapter();
        recyclerView.setAdapter(adapter);
        /*viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        noInternet = (TextView) findViewById(R.id.text_no_internet);*/

        getPresenter().getDataAsync(title,year,type)
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(this::success,this::error);
    }

    @OnClick(R.id.text_no_internet)
    public void onNoInternet(View view){
        Toast.makeText(this,"kliknalem no internet text",Toast.LENGTH_SHORT).show();

    }

    private void error(Throwable throwable) {
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInternet));
    }

    private void success(SearchResult searchResult) {
        if("False".equalsIgnoreCase(searchResult.getResposne())){
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(no_results));
        } else {
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(recyclerView));
        adapter.setItems(searchResult.getItems());
    }}

    /*public void setDataOnUiThread(SearchResult result, boolean isProblemWithInternet) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(isProblemWithInternet) {
                    error(throwable);
                } else {
                    success(result);
                }
            }
        });
    }*/


    public static Intent createIntent(Context context, String title, int year, String type) {
        Intent intent = new Intent(context, ListingActivity.class);
        intent.putExtra(SEARCH_TITLE, title);
        intent.putExtra(SEARCH_YEAR,year);
        intent.putExtra(SEARCH_TYPE,type);
        return intent;
    }


}
