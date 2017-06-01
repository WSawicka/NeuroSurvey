package com.sawicka.neurosurvey.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.sawicka.neurosurvey.AppTempData;
import com.sawicka.neurosurvey.R;
import com.sawicka.neurosurvey.utils.CommonsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends FragmentActivity {
    private AppTempData appTempData;
    List<String> listTitle;

    @BindView(R.id.commons_list) ExpandableListView commonsList;
    @BindView(R.id.question_list_dropdown) Spinner questionListDropdown;
    @BindView(R.id.new_item_common_name) EditText newItemCommonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.appTempData = getIntent().getParcelableExtra("APP_DATA");
        this.listTitle = new ArrayList<>(appTempData.getCommonAnswers().keySet());
        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, this.listTitle);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionListDropdown.setAdapter(adapter);
        setCommons();
    }

    public void setCommons(){
        CommonsAdapter expandableListAdapter = new CommonsAdapter(this.getApplicationContext(),
                this.appTempData.getCommonAnswers());
        commonsList.setAdapter(expandableListAdapter);

        /*commonsList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listTitle.get(groupPosition) + " List Collapsed.", Toast.LENGTH_SHORT).show(); }});*/

        commonsList.setOnItemClickListener(new ExpandableListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listTitle.get(position)
                                + " -> "
                                + appTempData.getCommonAnswers().get(
                                listTitle.get(position)), Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    @OnClick(R.id.add_common_item_button)
    public void addCommonItem(){
        String key = questionListDropdown.getSelectedItem().toString();
        String name = newItemCommonName.getText().toString();
        List<String> list = this.appTempData.getCommonAnswers().get(key);
        list.add(name);
        this.appTempData.getCommonAnswers().remove(key);
        this.appTempData.getCommonAnswers().put(key, list);
        newItemCommonName.getText().clear();
    }
}
