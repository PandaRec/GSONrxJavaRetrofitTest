package com.example.gsonrxjavaretrofittest.screens.employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.gsonrxjavaretrofittest.R;
import com.example.gsonrxjavaretrofittest.adapters.ResponseAdapter;
import com.example.gsonrxjavaretrofittest.api.ApiFactory;
import com.example.gsonrxjavaretrofittest.api.ApiService;
import com.example.gsonrxjavaretrofittest.pojo.Employee;
import com.example.gsonrxjavaretrofittest.pojo.EmployeeResponse;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EmployeeListActivity extends AppCompatActivity implements EmployeeListView {
    private ResponseAdapter responseAdapter;
    private RecyclerView recyclerView;
    private EmployeeListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.resycleView);
        responseAdapter = new ResponseAdapter();
        responseAdapter.setEmployees(new ArrayList<Employee>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(responseAdapter);
        presenter = new EmployeeListPresenter(this);
        presenter.LoadData();







    }

    @Override
    protected void onDestroy() {
        presenter.disposeDisposable();
        super.onDestroy();
    }

    @Override
    public void showData(List<Employee> employees) {
        responseAdapter.setEmployees(employees);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}