package com.example.expensetracingtablayout.RecyclerViewFiles;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracingtablayout.R;
import com.example.expensetracingtablayout.RoomDatabase.DatabaseHelper;
import com.example.expensetracingtablayout.RoomDatabase.Expense;
import com.example.expensetracingtablayout.RoomDatabase.ExpenseDAO;

import java.util.ArrayList;
import java.util.List;

public class RecyclerExpenseAdapter extends RecyclerView.Adapter<RecyclerExpenseAdapter.ViewHolder> {

    Context context;
    List<Expense> expenseList;
    private DatabaseHelper databaseHelper;



    public RecyclerExpenseAdapter(Context context, List<Expense> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rows, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        holder.txtTitle.setText(expenseModelArrayList.get(position).getTitle());     //(METHOD 2)
//        holder.txtAmount.setText(expenseModelArrayList.get(position).getAmount());

        holder.txtTitle.setText(expenseList.get(position).getTitle());   //(METHOD 1)
        holder.txtAmount.setText(expenseList.get(position).getAmount());



        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_update_layout);

                EditText edTitle = dialog.findViewById(R.id.dialogBoxUpdateTitle);
                EditText edAmount = dialog.findViewById(R.id.dialogBoxUpdateAmount);
                Button dialogBoxBtnAction = dialog.findViewById(R.id.dialogBoxBtnAction);
                TextView dialogBoxTxt = dialog.findViewById(R.id.dialogBoxText);


                edTitle.setText(expenseList.get(position).getTitle());
                edAmount.setText(expenseList.get(position).getAmount());


                dialogBoxBtnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Title, Amount;
                        Title = edTitle.getText().toString();
                        Amount = edAmount.getText().toString();

                        int itemId = expenseList.get(position).getId();

                        Expense updatedExpense = new Expense(itemId, Title, Amount);

                        databaseHelper = DatabaseHelper.getDB(context);
                        databaseHelper.expenseDAO().updateTx(updatedExpense);

                        // Update the ArrayList and notify the adapter
                        expenseList.set(position, new Expense(itemId, Title, Amount));
                        notifyItemChanged(position);

                        dialog.dismiss();
                    }
                });




                dialogBoxBtnAction.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(context, "Click to update Details", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                dialog.show();
            }
        });





    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtAmount;
        ConstraintLayout rowLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.rowsTitle);
            txtAmount = itemView.findViewById(R.id.rowsAmount);
            rowLayout = itemView.findViewById(R.id.row_layout);

        }
    }
}
