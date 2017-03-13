package com.example.rent.myapplication.search;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.example.rent.myapplication.listing.ListingActivity;
import com.example.rent.myapplication.listing.MovieListingItem;
import com.example.rent.myapplication.R;
import com.example.rent.myapplication.RetrofitProvider;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {

    private Map<Integer, String> apiKeysMap = new HashMap<Integer, String>() {{
        put(R.id.movies_radio, "movie");
        put(R.id.episodes_radio, "episode");
        put(R.id.games_radio, "game");
        put(R.id.TvSeries_radio, "series");
    }};
    @BindView(R.id.number_picker)
    NumberPicker numberPicker;

    @BindView(R.id.text_input_edit)
    TextInputEditText edit_text;

    @BindView(R.id.search_button)
    ImageButton search_button;

    @BindView(R.id.checkbox_year)
    CheckBox yearCheckbox;

    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    @BindView(R.id.type_checkbox)
    CheckBox typeCheckbox;

    @BindView(R.id.poster_header)
    RecyclerView posterHeaderRecyclerView;

    private PosterRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        numberPicker.setMinValue(1950);
        numberPicker.setMaxValue(year);
        numberPicker.setValue(year);
        numberPicker.setWrapSelectorWheel(true);


        adapter = new PosterRecyclerViewAdapter();
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
        posterHeaderRecyclerView.setLayoutManager(layoutManager);
        posterHeaderRecyclerView.setHasFixedSize(true);
        posterHeaderRecyclerView.setAdapter(adapter);

        posterHeaderRecyclerView.addOnScrollListener(new CenterScrollListener());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        RetrofitProvider retrofitProvider = (RetrofitProvider) getApplication();
        Retrofit retrofit = retrofitProvider.provideRetrofit();
        SearchService searchService = retrofit.create(SearchService.class);
        searchService.search(1, "m*", "2016", null)
                .flatMap(searchResult -> Observable.fromIterable(searchResult.getItems()))

                .map(MovieListingItem::getPoster)
                .filter(posterUrl -> !"N/A".equalsIgnoreCase(posterUrl))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(this::success, this::error);
    }
        /*RetrofitProvider retrofitProvider = (RetrofitProvider) getApplication();
        Retrofit retrofit = retrofitProvider.provideRetrofit();
        SearchService searchService = retrofit.create(SearchService.class);

                        searchService.search(1, "m*", "2016", null)
                .flatMap(searchResult -> Observable.fromIterable(searchResult.getItems()))
                .map(MovieListingItem::getPoster)
                .filter(posterUrl -> !"N/A".equalsIgnoreCase(posterUrl));*/


    // TextInputEditText edit_text = (TextInputEditText) findViewById(R.id.text_input_edit);
    //ImageButton search_button = (ImageButton) findViewById(R.id.search_button);
    private void success(List<String> list) {
        adapter.setUrls(list);
    }

    private void error(Throwable throwable) {

    }

    @OnCheckedChanged(R.id.type_checkbox)
    void onTypeChackeboxChangeState(CompoundButton buttonView, boolean isChecked) {
        radioGroup.setVisibility(isChecked ? View.VISIBLE : View.GONE);
    }

    @OnCheckedChanged(R.id.checkbox_year)
    void onYearCheckboxStateChanged(CompoundButton buttonView, boolean isChecked) {
        numberPicker.setVisibility(isChecked ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.search_button)
    void onSearchButtonClick() {
        int checkedRadioId = radioGroup.getCheckedRadioButtonId();
        String typeKey = typeCheckbox.isChecked() ? apiKeysMap.get(checkedRadioId) : null;

        int year = yearCheckbox.isChecked() ? numberPicker.getValue() : ListingActivity.NO_YEAR_SELECTED;

        startActivity(ListingActivity.createIntent(SearchActivity.this,
                edit_text.getText().toString(),
                year, typeKey));
    }
}
