package com.elishou.anesthesiaplan.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PlanDao {

    @Insert
    void insert(Plan plan);

    @Update
    void update(Plan plan);

    @Delete
    void delete(Plan plan);

    @Delete
    void deletePlans(Plan... plans);

    @Transaction
    @Query("SELECT * FROM " + Plan.TABLE_NAME + " ORDER BY " + Plan.COLUMN_CREATED_AT + " DESC")
    LiveData<List<PlanWithQuestions>> getAllPlans();

    @Transaction
    @Query("SELECT * FROM " + Plan.TABLE_NAME + " WHERE " + Plan.COLUMN_ID + " = :id")
    LiveData<PlanWithQuestions> getPlanById(String id);
}
