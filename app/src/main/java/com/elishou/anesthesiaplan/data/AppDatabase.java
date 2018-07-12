package com.elishou.anesthesiaplan.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {
                Plan.class,
                Question.class,
                Choice.class},
          version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PlanDao planDao();

    public abstract QuestionDao questionDao();

    public abstract ChoiceDao choiceDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "plan_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
