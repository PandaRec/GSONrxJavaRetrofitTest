package com.example.gsonrxjavaretrofittest.screens.employee;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gsonrxjavaretrofittest.api.ApiFactory;
import com.example.gsonrxjavaretrofittest.api.ApiService;
import com.example.gsonrxjavaretrofittest.data.AppDatabase;
import com.example.gsonrxjavaretrofittest.pojo.Employee;
import com.example.gsonrxjavaretrofittest.pojo.EmployeeResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EmployeeViewModel extends AndroidViewModel {
    private static AppDatabase database;
    private LiveData<List<Employee>> employees;
    private MutableLiveData<Throwable> errors;
    private CompositeDisposable compositeDisposable;

    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        database = AppDatabase.getInstance(application);
        employees = database.employeeDao().getAllEmployees();
        errors = new MutableLiveData<>();
    }

    public void LoadData(){
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();


        Disposable disposable = apiService.getResponseService().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<EmployeeResponse>() {
                    @Override
                    public void accept(EmployeeResponse response) throws Exception {
                        deleteAllEmployees();
                        insertEmployees(response.getResponse());
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //view.showError(throwable.getMessage());
                        errors.postValue(throwable);
                    }
                });
        compositeDisposable.add(disposable);
    }

    public LiveData<List<Employee>> getEmployees() {
        return employees;
    }

    public LiveData<Throwable> getErrors() {
        return errors;
    }
    public void clearErrors(){
        errors.setValue(null);
    }

    public void insertEmployees(List<Employee> employees){
        new InsertEmployeesTask().execute(employees);
    }
    public void deleteAllEmployees(){
        new DeleteAllEmployees().execute();
    }


    private static class InsertEmployeesTask extends AsyncTask<List<Employee>,Void,Void>{
        @Override
        protected final Void doInBackground(List<Employee>... lists) {
            if(lists!=null && lists.length>0){
                database.employeeDao().insertEmployees(lists[0]);
            }
            return null;
        }
    }
    private static class DeleteAllEmployees extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            database.employeeDao().deleteAllEmployees();
            return null;
        }
    }

    @Override
    protected void onCleared() {
        if(compositeDisposable!=null)
            compositeDisposable.dispose();
        super.onCleared();
    }
}
