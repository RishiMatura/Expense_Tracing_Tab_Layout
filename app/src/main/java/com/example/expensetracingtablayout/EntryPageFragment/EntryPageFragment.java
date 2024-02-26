package com.example.expensetracingtablayout.EntryPageFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expensetracingtablayout.R;
import com.example.expensetracingtablayout.RecyclerViewFiles.RecyclerViewActivity;
import com.example.expensetracingtablayout.RoomDatabase.DatabaseHelper;
import com.example.expensetracingtablayout.RoomDatabase.Expense;

import java.util.ArrayList;
import java.util.List;

public class EntryPageFragment extends Fragment {

    EditText edTitle, edAmount;
    Button btnAdd, btnRecyclerView;
    Button btnDelete;
    private List<Expense> allExpenses;
    private ConstraintLayout constraintLayout;
    private DatabaseHelper databaseHelper;

    public EntryPageFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_entry_page, container, false);

        // Initialize databaseHelper
        databaseHelper = DatabaseHelper.getDB(getContext());

        edTitle = view.findViewById(R.id.enterTitle);
        edAmount = view.findViewById(R.id.enterAmount);
        btnAdd = view.findViewById(R.id.addButton);
        btnRecyclerView = view.findViewById(R.id.btnRecyclerView);
        btnDelete = view.findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edTitle.getText().toString();
                String amount = edAmount.getText().toString();
                databaseHelper.expenseDAO().addTx(new Expense(title, amount));
                Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();

                ArrayList<Expense> arrExpense = (ArrayList<Expense>) databaseHelper.expenseDAO().getAllExpense();
                for (int i = 0; i < arrExpense.size(); i++) {
                    Log.d("Data", "Title " + arrExpense.get(i).getTitle() + " Amount " + arrExpense.get(i).getAmount());
                }

                edTitle.setText("");
                edAmount.setText("");
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allExpenses = databaseHelper.expenseDAO().getAllExpense();
                if (!allExpenses.isEmpty()) {
                    databaseHelper.expenseDAO().deleteLastTx();
                    Toast.makeText(getContext(), "Removed", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle case where there are no expenses to delete
                    Toast.makeText(getContext(), "No expenses to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RecyclerViewActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
