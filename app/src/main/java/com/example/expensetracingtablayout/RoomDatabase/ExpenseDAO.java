package com.example.expensetracingtablayout.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpenseDAO {
    @Query("select * from Expense")
    List<Expense> getAllExpense();

    @Insert
    void addTx(Expense expense);

    @Update
    void updateTx(Expense expense);

    @Query("DELETE FROM Expense WHERE id = (SELECT MAX(id) FROM Expense)")
    void deleteLastTx();



}
