package com.example.gsonrxjavaretrofittest.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsonrxjavaretrofittest.R;
import com.example.gsonrxjavaretrofittest.pojo.Employee;

import java.util.List;

public class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.ResponseViewHolder> {

    List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResponseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item,parent,false);
        return new ResponseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResponseViewHolder holder, int position) {
        Employee employee  = employees.get(position);
        holder.textViewName.setText(employee.getFirstName());
        holder.textViewLastName.setText(employee.getLastName());
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public class ResponseViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewName;
        private TextView textViewLastName;

        public ResponseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLastName = itemView.findViewById(R.id.textViewLastName);
            textViewName = itemView.findViewById(R.id.textViewName);

        }
    }
}
