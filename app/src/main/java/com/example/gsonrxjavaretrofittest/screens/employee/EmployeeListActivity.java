package com.example.gsonrxjavaretrofittest.screens.employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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

public class EmployeeListActivity extends AppCompatActivity {
    private ResponseAdapter responseAdapter;
    private RecyclerView recyclerView;
    private EmployeeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.resycleView);
        responseAdapter = new ResponseAdapter();
        responseAdapter.setEmployees(new ArrayList<Employee>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(responseAdapter);


        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(EmployeeViewModel.class);

        viewModel.getEmployees().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employees) {
                responseAdapter.setEmployees(employees);
            }
        });
        viewModel.getErrors().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if(throwable!=null){
                    Toast.makeText(EmployeeListActivity.this, "ошибка - "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    viewModel.clearErrors();
                }
            }
        });

        viewModel.LoadData();






    }

}
