package com.example.gsonrxjavaretrofittest.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gsonrxjavaretrofittest.pojo.Employee;

import java.util.List;

@Dao
public interface EmployeeDao {
    @Query("select * from employees")
    LiveData<List<Employee>> getAllEmployees();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEmployees(List<Employee> employees);

    @Query("delete from employees")
    void deleteAllEmployees();


}
