package com.elishou.anesthesiaplan.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

@Dao
public interface ChoiceDao {

    @Insert
    void insert(Choice choice);

    @Update
    void update(Choice choice);

}
