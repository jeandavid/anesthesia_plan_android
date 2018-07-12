package com.elishou.anesthesiaplan.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import org.parceler.Parcel;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Parcel
@Entity(tableName = Plan.TABLE_NAME)
public class Plan {

    public static final String TABLE_NAME = "plans";

    public static final String COLUMN_ID = BaseColumns._ID;

    public static final String COLUMN_CREATED_AT = "created_at";

    @PrimaryKey
    @NonNull
    @ColumnInfo(index = true, name = COLUMN_ID)
    public String id;

    @ColumnInfo(name = COLUMN_CREATED_AT)
    @NonNull
    public Date createdAt;

    @Ignore
    public Plan() {
        this(UUID.randomUUID().toString(), Calendar.getInstance().getTime());
    }

    @Ignore
    public Plan(@NonNull String id) {
        this(id, Calendar.getInstance().getTime());
    }

    public Plan(@NonNull String id, @NonNull Date createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }
}
