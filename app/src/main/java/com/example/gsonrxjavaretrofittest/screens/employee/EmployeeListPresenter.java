package com.example.gsonrxjavaretrofittest.screens.employee;

import android.util.Log;
import android.widget.Toast;

import com.example.gsonrxjavaretrofittest.api.ApiFactory;
import com.example.gsonrxjavaretrofittest.api.ApiService;
import com.example.gsonrxjavaretrofittest.pojo.EmployeeResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EmployeeListPresenter {
    private CompositeDisposable compositeDisposable;
    private EmployeeListView view;

    public EmployeeListPresenter(EmployeeListView view) {
        this.view = view;
    }

    public void LoadData(){
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();


        Disposable disposable = apiService.getResponseService().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<EmployeeResponse>() {
                    @Override
                    public void accept(EmployeeResponse response) throws Exception {
                        //ставим значения в адаптер
                        view.showData(response.getResponse());
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showError(throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void disposeDisposable(){
        if(compositeDisposable!=null)
            compositeDisposable.dispose();
    }
}
