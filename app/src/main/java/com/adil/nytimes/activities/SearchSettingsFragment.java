package com.adil.nytimes.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.adil.nytimes.R;
import com.adil.nytimes.models.NewsDesk;
import com.adil.nytimes.models.Settings;
import com.adil.nytimes.models.SortOrder;
import com.adil.nytimes.utils.DateUtil;

import java.text.ParseException;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by adil on 2/12/16.
 */
public class SearchSettingsFragment extends DialogFragment {

    @Bind(R.id.switchFilterEnabled) Switch switchFilterEnabled;
    @Bind(R.id.tvDatePicker) TextView tvDatePicker;
    @Bind(R.id.spinnerNewsDesk) Spinner spinnerNewsDesk;
    @Bind(R.id.spinnerSortOrder) Spinner spinnerSortOrder;
    @Bind(R.id.btnSave) Button btnSave;
    @Bind(R.id.btnReset) Button btnReset;


    private Settings filter;
    private DatePickerDialog dateDialog;
    private ArrayAdapter<SortOrder> sortOrderAdapter;
    private ArrayAdapter<NewsDesk> newsDeskArrayAdapter;

    public SearchSettingsFragment(){}

    public static SearchSettingsFragment newInstance(){
        SearchSettingsFragment frag = new SearchSettingsFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_settings, container);
        ButterKnife.bind(this, view);
        filter = Settings.getInstance(getContext());

        setDateField();

        tvDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
            }
        });

        sortOrderAdapter = new ArrayAdapter<SortOrder>(getActivity(), R.layout.support_simple_spinner_dropdown_item, SortOrder.values());
        spinnerSortOrder.setAdapter(sortOrderAdapter);

        newsDeskArrayAdapter = new ArrayAdapter<NewsDesk>(getActivity(), R.layout.support_simple_spinner_dropdown_item, NewsDesk.values());
        spinnerNewsDesk.setAdapter(newsDeskArrayAdapter);

        return view;
    }

    private void setDateField(){
        Calendar newCalendar = Calendar.getInstance();
        dateDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvDatePicker.setText(DateUtil.getDateAsString(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvDatePicker.setText(filter.getBeginDateForView());
        switchFilterEnabled.setChecked(filter.getFilterEnabled());
        spinnerSortOrder.setSelection(sortOrderAdapter.getPosition(filter.getSortOrderForView()));
        spinnerNewsDesk.setSelection(newsDeskArrayAdapter.getPosition(filter.getNewsDeskForView()));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    filter.setFilterEnabled(switchFilterEnabled.isChecked());
                    filter.setBeginDate(DateUtil.getStringAsDate((String) tvDatePicker.getText()));
                    filter.setSortOrder(SortOrder.get(spinnerSortOrder.getSelectedItem().toString()));
                    filter.setNewsDesk(NewsDesk.get(spinnerNewsDesk.getSelectedItem().toString()));
                    onDestroyView();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
