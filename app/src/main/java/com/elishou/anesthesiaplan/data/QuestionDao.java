package com.elishou.anesthesiaplan.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

@Dao
public interface QuestionDao {

    @Insert
    void insert(Question question);

    @Update
    void update(Question question);

}
