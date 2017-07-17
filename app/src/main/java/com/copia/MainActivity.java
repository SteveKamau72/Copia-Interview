package com.copia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    @BindView(R.id.countries_recyclerview)
    RecyclerView countriesRecyclerView;
    List<CountriesModel> countriesModelList = new ArrayList<>();
    UserDataBase userDataBase;
    CountriesAdapter countriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpToolBar();
        setViews();
        userDataBase = new UserDataBase(this);
    }

    private void setUpToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    private void setViews() {
        countriesAdapter = new CountriesAdapter(getApplicationContext(), countriesModelList);
        countriesModelList.clear();
        countriesModelList.add(new CountriesModel("Kenya"));
        countriesModelList.add(new CountriesModel("Morocco"));
        countriesModelList.add(new CountriesModel("Uganda"));
        countriesModelList.add(new CountriesModel("Sudan"));
        countriesModelList.add(new CountriesModel("Somali"));
        countriesModelList.add(new CountriesModel("Tanzania"));
        countriesModelList.add(new CountriesModel("Zambia"));
        countriesModelList.add(new CountriesModel("SouthAfrica"));
        countriesModelList.add(new CountriesModel("Eritrea"));
        countriesModelList.add(new CountriesModel("Nigeria"));
    }

    private void setAdapter() {
        LinearLayoutManager Llm = new LinearLayoutManager(this);
        countriesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        countriesRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        countriesRecyclerView.setHasFixedSize(true);
        countriesRecyclerView.setLayoutManager(Llm);
        countriesRecyclerView.setItemViewCacheSize(countriesModelList.size());
        countriesRecyclerView.setAdapter(countriesAdapter);
        countriesAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.log_out)
    void logOut() {//log out user
        userDataBase.deleteUser();
        Toast.makeText(this, "Successfully logged out!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_countries) {
            setAdapter();

            return true;
        }
        if (id == R.id.action_edit) {
            showEditForm();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showEditForm() {
        startActivity(new Intent(getApplicationContext(), EditFormActivity.class));
    }

}
