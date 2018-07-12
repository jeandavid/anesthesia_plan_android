package com.elishou.anesthesiaplan.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.Objects;
import java.util.UUID;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Parcel
@Entity(tableName = Choice.TABLE_NAME,
        foreignKeys = @ForeignKey(
                entity = Question.class,
                parentColumns = Question.COLUMN_ID,
                childColumns = Choice.COLUMN_QUESTION_ID,
                onDelete = CASCADE
            )
        )
public class Choice implements Comparable<Choice> {

    public static final String TABLE_NAME = "choices";

    public static final String COLUMN_ID = BaseColumns._ID;

    public static final String COLUMN_BODY = "body";

    public static final String COLUMN_SELECTED = "selected";

    public static final String COLUMN_POSITION = "position";

    public static final String COLUMN_QUESTION_ID = "question_id";

    @PrimaryKey
    @NonNull
    @ColumnInfo(index = true, name = COLUMN_ID)
    public String id;

    @ColumnInfo(name = COLUMN_BODY)
    public String body;

    @ColumnInfo(name = COLUMN_SELECTED)
    public boolean selected;

    @ColumnInfo(name = COLUMN_POSITION)
    public int position;

    @ColumnInfo(index = true, name = COLUMN_QUESTION_ID)
    @NonNull
    public String questionId;

    @Ignore
    public Choice(String body, int position, String questionId) {
        this(UUID.randomUUID().toString(), body, false, position, questionId);
    }

    @ParcelConstructor
    public Choice(@NonNull String id, String body, boolean selected, int position, @NonNull String questionId) {
        this.id = id;
        this.body = body;
        this.selected = selected;
        this.position = position;
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Choice choice = (Choice) o;
        return Objects.equals(choice.id, id);
    }

    @Override
    public int compareTo(@NonNull Choice o) {
        return Integer.compare(position, o.position);
    }
}
