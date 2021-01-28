package com.example.gsonrxjavaretrofittest.screens.employee;

import com.example.gsonrxjavaretrofittest.pojo.Employee;

import java.util.List;

public interface EmployeeListView {
    void showData(List<Employee> employees);
    void showError(String error);

}
