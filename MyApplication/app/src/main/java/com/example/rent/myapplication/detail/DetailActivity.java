package com.example.rent.myapplication.detail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rent.myapplication.R;
import com.example.rent.myapplication.RetrofitProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

@RequiresPresenter(DetailPresenter.class)
public class DetailActivity extends NucleusAppCompatActivity<DetailPresenter> {
    private static final String ID_KEY = "id_key";
    private Disposable subscribe;
    @BindView(R.id.poster)
    ImageView poster;

    @BindView(R.id.title_and_year)
    TextView title_and_year;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.actors)
    TextView actors;
    @BindView(R.id.plot)
    TextView plot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        ButterKnife.bind(this);

        String imdbId = getIntent().getStringExtra(ID_KEY);
        RetrofitProvider retrofitProvider = (RetrofitProvider) getApplication();
        getPresenter().setRetrofit(retrofitProvider.provideRetrofit());

        subscribe = getPresenter().loadDetail(imdbId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::success, this::error);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    private void error(Throwable throwable) {
    }

    private void success(MovieItem movieItem) {
        Glide.with(this).load(movieItem.getPoster()).into(poster);
      /* title_and_year.setText(movieItem.getTitle());
        type.setText(movieItem.getType());
        actors.setText(movieItem.getActors());
        plot.setText(movieItem.getPlot());*/

    }

    public static Intent createIntent(Context context, String imdbID ){
        Intent intent = new Intent(context,DetailActivity.class);
        intent.putExtra(ID_KEY,imdbID);
        return intent;
    }
}
