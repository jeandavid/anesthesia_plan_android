package com.elishou.anesthesiaplan.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.Objects;
import java.util.Observable;
import java.util.UUID;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Parcel
@Entity(tableName = Question.TABLE_NAME,
        foreignKeys = @ForeignKey(
                entity = Plan.class,
                parentColumns = Plan.COLUMN_ID,
                childColumns = Question.COLUMN_PLAN_ID,
                onDelete = CASCADE
            )
        )
public class Question {

    public static final String TABLE_NAME = "questions";

    public static final String COLUMN_ID = BaseColumns._ID;

    public static final String COLUMN_PROBLEM = "problem";

    public static final String COLUMN_PLAN_ID = "plan_id";

    public static final String COLUMN_STEP = "step";

    public static final String COLUMN_INPUT= "input";

    @PrimaryKey
    @NonNull
    @ColumnInfo(index = true, name = COLUMN_ID)
    public String id;

    @ColumnInfo(name = COLUMN_PROBLEM)
    @NonNull
    public String problem;

    @ColumnInfo(index = true, name = COLUMN_PLAN_ID)
    @NonNull
    public String planId;

    @ColumnInfo(name = COLUMN_STEP)
    public int step;

    @ColumnInfo(name = COLUMN_INPUT)
    @Nullable
    public String input;

    @Ignore
    public Question(@NonNull String problem, @NonNull String planId, int step) {
        this(problem, planId, step,null);
    }

    @Ignore
    public Question(@NonNull String problem, @NonNull String planId, int step, @Nullable String input) {
        this(UUID.randomUUID().toString(), problem, planId, step, input);
    }

    @ParcelConstructor
    public Question(@NonNull String id, @NonNull String problem, @NonNull String planId, int step, @Nullable String input) {
        this.id = id;
        this.problem = problem;
        this.planId = planId;
        this.step = step;
        this.input = input;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Question question = (Question) obj;
        return Objects.equals(id, question.id);
    }
}
