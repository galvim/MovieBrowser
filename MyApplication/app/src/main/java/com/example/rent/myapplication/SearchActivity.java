package com.example.rent.myapplication;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

    private Map<Integer, String> apiKeysMap = new HashMap<Integer, String>(){{
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



        // TextInputEditText edit_text = (TextInputEditText) findViewById(R.id.text_input_edit);
        //ImageButton search_button = (ImageButton) findViewById(R.id.search_button);
    }

    @OnCheckedChanged(R.id.type_checkbox)
    void onTypeChackeboxChangeState(CompoundButton buttonView, boolean isChecked){
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
                    year,typeKey ));
        }
}
