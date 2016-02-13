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
import com.adil.nytimes.models.Article;
import com.adil.nytimes.network.NYTimesApiClient;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    @Bind(R.id.rvArticles) RecyclerView rvArticles;


    private NYTimesApiClient apiClient;
    public static ArticlesAdapter articlesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_search);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        articlesAdapter = new ArticlesAdapter(new ArrayList<Article>());
        rvArticles.setAdapter(articlesAdapter);
        rvArticles.setLayoutManager(layoutManager);
        rvArticles.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        final SearchView searchView =  (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // callback goes here to perform query
                apiClient = new NYTimesApiClient();
                apiClient.fetchArticles(query);
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        MenuItem filterButton = menu.findItem(R.id.action_search_filter_button);
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
