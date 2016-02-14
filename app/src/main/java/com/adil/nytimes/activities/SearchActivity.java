package com.adil.nytimes.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.adil.nytimes.R;
import com.adil.nytimes.adapters.ArticlesAdapter;
import com.adil.nytimes.interfaces.EndlessRecyclerViewScrollListener;
import com.adil.nytimes.models.Article;
import com.adil.nytimes.models.Settings;
import com.adil.nytimes.network.NYTimesApiClient;
import com.adil.nytimes.utils.NetworkUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    @Bind(R.id.rvArticles) RecyclerView rvArticles;
    MenuItem searchItem;
    public static MenuItem filterButton;


    private NYTimesApiClient apiClient;
    private Settings filter;
    public static ArticlesAdapter articlesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_search);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        filter = Settings.getInstance(this);
        NetworkUtils.verifyConnectivity(this);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        articlesAdapter = new ArticlesAdapter(new ArrayList<Article>());
        rvArticles.setAdapter(articlesAdapter);
        rvArticles.setLayoutManager(layoutManager);
        rvArticles.setHasFixedSize(true);
        rvArticles.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                apiClient = new NYTimesApiClient(getApplicationContext());
                apiClient.fetchArticles(page);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        searchItem = menu.findItem(R.id.action_search);

        final SearchView searchView =  (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                apiClient = new NYTimesApiClient(getApplicationContext());
                apiClient.fetchArticles(query, 0);
                Toast.makeText(getApplicationContext(), "Searching for "+ query, Toast.LENGTH_SHORT).show();
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        filterButton = menu.findItem(R.id.action_search_filter_button);
        if(filter.getFilterEnabled())
            filterButton.setIcon(R.drawable.ic_filter_orange_24);
        filterButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showSearchSettingsDialog();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void showSearchSettingsDialog(){
        FragmentActivity fragmentActivity = (FragmentActivity) this;
        FragmentManager fm = fragmentActivity.getSupportFragmentManager();
        SearchSettingsFragment settingsDialog = SearchSettingsFragment.newInstance();
        settingsDialog.show(fm, "tag");
    }
}
