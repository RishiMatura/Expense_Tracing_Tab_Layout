package com.example.expensetracingtablayout.RecyclerViewFiles;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracingtablayout.RoomDatabase.DatabaseHelper;
import com.example.expensetracingtablayout.RoomDatabase.Expense;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView emptyMessageTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = findViewById(R.id.recyclerExpense);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyMessageTextView = findViewById(R.id.emptyMessageTextView);

        getRoomData();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getRoomData() {

//        Warning !!!! DO NOT CHANGE, SINCE BEING THE DEVELOPER EVEN I DON'T KNOW HOW THIS IS WORKING !!!

/*       Date Feb 21, 2024.
        I think I have figured out the working of both the methods(WAYS),
        1ST METHOD:
        EXPLANATION:

        1st in the current way we are using an ArrayList<ExpenseModel> to hold the data for our RecyclerView.

        This ArrayList is populated by iterating over the list of Expense objects obtained from the
        database and converting each Expense object into an ExpenseModel object.
        Then, we pass this ArrayList to the adapter, which is responsible for binding the data to the RecyclerView.

        This gives an extra layer of abstraction in comparison to the 2nd METHOD.

        *-------------------------------------------------------------------------------------------------------------*

        2nd METHOD:

        JUST uncomment Method 2 block and you will find the usage to the 2nd METHOD.
        EXPLANATION:

        In the updated code, I have removed the ExpenseModel class and directly passed a List<Expense> to the adapter
        instead of creating an intermediate list of ExpenseModel objects.

        So, instead of converting Expense objects into ExpenseModel objects, I'm just passing the Expense objects
        directly to the adapter. This makes the code cleaner and easier to understand because I'm not adding
        unnecessary complexity by introducing another class.

*/
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);  //Common in both Methods,
        // used to instigate the DatabaseHelper class


//            METHOD 1

        ArrayList<ExpenseModel> expenseModelArrayList = new ArrayList<>();
        List<Expense> arrExpense = databaseHelper.expenseDAO().getAllExpense();

        if (arrExpense.isEmpty()) {
            // Dataset is empty, show the empty message
            emptyMessageTextView.setVisibility(View.VISIBLE);
        } else {
            // Dataset is not empty, hide the empty message
            emptyMessageTextView.setVisibility(View.GONE);

            for (Expense expense : arrExpense) {
                expenseModelArrayList.add(new ExpenseModel(expense.getTitle(), expense.getAmount()));
            }
            RecyclerExpenseAdapter adapter = new RecyclerExpenseAdapter(this, expenseModelArrayList);
            recyclerView.setAdapter(adapter);
        }

//        for (Expense expense : arrExpense) {
//            expenseModelArrayList.add(new ExpenseModel(expense.getTitle(), expense.getAmount()));
//        }
//        RecyclerExpenseAdapter adapter = new RecyclerExpenseAdapter(this, expenseModelArrayList);
//        recyclerView.setAdapter(adapter);


//            adapter.notifyDataSetChanged();


//            METHOD 2

//            List<Expense> arrExpense = databaseHelper.expenseDAO().getAllExpense();
//            RecyclerExpenseAdapter adapter = new RecyclerExpenseAdapter(this, arrExpense);
//            recyclerView.setAdapter(adapter);




    }
}