package com.example.expensetracingtablayout.EntryPageFragment;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expensetracingtablayout.R;
import com.example.expensetracingtablayout.RecyclerViewFiles.RecyclerViewActivity;
import com.example.expensetracingtablayout.RoomDatabase.DatabaseHelper;
import com.example.expensetracingtablayout.RoomDatabase.Expense;

import java.util.ArrayList;
import java.util.List;

public class EntryPageFragment extends Fragment implements View.OnTouchListener {

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
        constraintLayout = view.findViewById(R.id.parentLayout);

//         Attaching the onTouchListener to each view

        edTitle.setOnTouchListener(this);
        edAmount.setOnTouchListener(this);
        btnAdd.setOnTouchListener(this);
        btnRecyclerView.setOnTouchListener(this);
        btnDelete.setOnTouchListener(this);
        constraintLayout.setOnTouchListener(this);


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

    //    This method is handling the onTouch feature, when the user clicks on anyView or parentLayout,
    //    the cursor along with the keyboard disappears.
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Check if the touch event is outside the EditText fields
        if (event.getAction() == MotionEvent.ACTION_DOWN && !(v instanceof EditText)) {
            // Hide soft keyboard
            InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            // Clear focus from EditText fields
            edTitle.clearFocus();
            edAmount.clearFocus();
        }
        return false;
    }
}
