package com.adil.nytimes.activities;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adil.nytimes.R;

import butterknife.ButterKnife;

/**
 * Created by adil on 2/12/16.
 */
public class SearchSettingsFragment extends DialogFragment {

    public SearchSettingsFragment(){}

    public static SearchSettingsFragment newInstance(){
        SearchSettingsFragment frag = new SearchSettingsFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Settings");
        View view = inflater.inflate(R.layout.fragment_search_settings, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
