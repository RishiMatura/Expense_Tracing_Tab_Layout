package com.example.expensetracingtablayout.RecyclerViewFiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracingtablayout.R;

import java.util.ArrayList;

public class RecyclerExpenseAdapter extends RecyclerView.Adapter<RecyclerExpenseAdapter.ViewHolder> {

    Context context;
//    METHOD 1 BELOW:

//    List<Expense> expenseModelArrayList;

    //      METHOD 2 BELOW:
    ArrayList<ExpenseModel> expenseModelArrayList;

    {

//        METHOD 2  Constructor to ArrayList of ExpenseModel type


/*        For method 2, change the constructor's arguments to ArrayList and the declaration also to ArrayList
        Also use getTitle() and getAmount() methods.


    public RecyclerExpenseAdapter(ArrayList < ExpenseModel > expenseModelArrayList) {
        this.expenseModelArrayList = expenseModelArrayList;
    }
 */

    }

    public RecyclerExpenseAdapter(Context context, ArrayList<ExpenseModel> expenseModelArrayList) {
        this.context = context;
        this.expenseModelArrayList = expenseModelArrayList;
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

        holder.txtTitle.setText(expenseModelArrayList.get(position).title);   //(METHOD 1)
        holder.txtAmount.setText(expenseModelArrayList.get(position).amount);
    }

    @Override
    public int getItemCount() {
        return expenseModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.rowsTitle);
            txtAmount = itemView.findViewById(R.id.rowsAmount);


        }
    }
}
