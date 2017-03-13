package com.example.rent.myapplication;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private static final double PAGE_SIZE = 10;
    private int totalItemsNumber;
    boolean isLoading;
    OnLoadNextPageListener listener;
    private CurrentItemListener currentItemListener;
    private ShoworHideCounter showorHideCounter;
    private boolean isCounterShown;

    public void setShoworHideCounter(ShoworHideCounter showorHideCounter) {
        this.showorHideCounter = showorHideCounter;
    }

    public void setTotalItemsNumber(int totalItemsNumber) {
        this.totalItemsNumber = totalItemsNumber;
        isLoading = false;
    }

    private LinearLayoutManager layoutManager;

    public EndlessScrollListener(LinearLayoutManager layoutManager, OnLoadNextPageListener listener) {
        this.layoutManager = layoutManager;
        this.listener = listener;
    }

    public void setCurrentItemListener(CurrentItemListener currentItemListener) {
        this.currentItemListener = currentItemListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int alreadyLoadedItems = layoutManager.getItemCount();
        int currentPage = (int) (Math.ceil(alreadyLoadedItems / PAGE_SIZE));
        double numberOfAllPages = Math.ceil(totalItemsNumber / PAGE_SIZE);
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition() + 1;

        if (currentPage < numberOfAllPages && lastVisibleItemPosition == alreadyLoadedItems && !isLoading) {
            loadNextPage(++currentPage);
            isLoading = true;
        }

        if (currentItemListener != null) {
            currentItemListener.onNewCurrentItem(lastVisibleItemPosition, totalItemsNumber);
        }

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
    super.onScrollStateChanged(recyclerView,newState);
        if (showorHideCounter !=null) {
            if (isCounterShown && newState == recyclerView.SCROLL_STATE_IDLE) {
                showorHideCounter.hideCounter();
                isCounterShown = false;
            } else if(!isCounterShown && newState == recyclerView.SCROLL_STATE_DRAGGING){
               showorHideCounter.showCounter();
                isCounterShown=true;
            }
        }
    }

    private void loadNextPage(int pageNumber) {
        listener.loadNextPage(pageNumber);

    }
}
