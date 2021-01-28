package com.example.gsonrxjavaretrofittest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.gsonrxjavaretrofittest.adapters.ResponseAdapter;
import com.example.gsonrxjavaretrofittest.api.ApiFactory;
import com.example.gsonrxjavaretrofittest.api.ApiService;
import com.example.gsonrxjavaretrofittest.pojo.Employee;
import com.example.gsonrxjavaretrofittest.pojo.EmployeeResponse;

import java.util.ArrayList;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private CompositeDisposable compositeDisposable;
    private ResponseAdapter responseAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.resycleView);
        responseAdapter = new ResponseAdapter();
        responseAdapter.setEmployees(new ArrayList<Employee>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(responseAdapter);
        compositeDisposable = new CompositeDisposable();



        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();



        Disposable disposable = apiService.getResponseService().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<EmployeeResponse>() {
            @Override
            public void accept(EmployeeResponse response) throws Exception {
                    //ставим значения в адаптер
                Log.i("my_ex",response.getResponse().toString());
                responseAdapter.setEmployees(response.getResponse());
            }

        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i("my_ex",throwable.getMessage());
                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        compositeDisposable.add(disposable);


    }

    @Override
    protected void onDestroy() {
        if(compositeDisposable!=null){
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }
}