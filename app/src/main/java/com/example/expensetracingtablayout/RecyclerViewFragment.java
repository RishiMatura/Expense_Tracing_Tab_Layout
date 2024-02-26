package com.example.expensetracingtablayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.expensetracingtablayout.R;
import com.example.expensetracingtablayout.RecyclerViewFiles.ExpenseModel;
import com.example.expensetracingtablayout.RecyclerViewFiles.RecyclerExpenseAdapter;
import com.example.expensetracingtablayout.RoomDatabase.DatabaseHelper;
import com.example.expensetracingtablayout.RoomDatabase.Expense;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragment extends Fragment {
    RecyclerView recyclerView;
    TextView emptyMessageTextView;
    RecyclerExpenseAdapter adapter;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        recyclerView = view.findViewById(R.id.recyclerExpenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        emptyMessageTextView = view.findViewById(R.id.emptyMessageTextView);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        refreshData();

        getRoomData();
    }

    public void getRoomData() {
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(getContext());
        List<Expense> arrExpense = databaseHelper.expenseDAO().getAllExpense();
        ArrayList<ExpenseModel> expenseModelArrayList = new ArrayList<>();

        for (Expense expense : arrExpense) {
            expenseModelArrayList.add(new ExpenseModel(expense.getTitle(), expense.getAmount()));
        }

        if (expenseModelArrayList.isEmpty()) {
            // Dataset is empty, show the empty message
            emptyMessageTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE); // Hide the RecyclerView
        } else {
            // Dataset is not empty, hide the empty message
            emptyMessageTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE); // Show the RecyclerView

            RecyclerExpenseAdapter adapter = new RecyclerExpenseAdapter(getContext(), expenseModelArrayList);
            recyclerView.setAdapter(adapter);
        }
    }


}



