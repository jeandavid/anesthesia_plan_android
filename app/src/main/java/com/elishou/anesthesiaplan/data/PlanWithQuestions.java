package com.elishou.anesthesiaplan.data;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import android.util.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlanWithQuestions {

    @Embedded
    public Plan plan;

    @Relation(parentColumn = Plan.COLUMN_ID, entityColumn = Question.COLUMN_PLAN_ID, entity = Question.class)
    public List<MultiChoice> multiChoices;

    // Init a new plan with a list of questions.
    // This list might evolve in the future.
    // This app is designed to allow such evolutions.
    public PlanWithQuestions() {
        this.plan = new Plan();

        this.multiChoices = Arrays.asList(
                new MultiChoice(this.plan.id, 0),
                new MultiChoice(this.plan.id, 1),
                new MultiChoice(this.plan.id, 2),
                new MultiChoice(this.plan.id, 3),
                new MultiChoice(this.plan.id, 4),
                new MultiChoice(this.plan.id, 5),
                new MultiChoice(this.plan.id, 6),
                new MultiChoice(this.plan.id, 7),
                new MultiChoice(this.plan.id, 8),
                new MultiChoice(this.plan.id, 9),
                new MultiChoice(this.plan.id, 10),
                new MultiChoice(this.plan.id, 11),
                new MultiChoice(this.plan.id, 12)
        );
    }

    public int getNumberOfQuestions() {
        return this.multiChoices.size();
    }

    // Sort questions based on their step, and each question choice list
    public void sort() {
        if (multiChoices == null) return;
        Collections.sort(multiChoices);
        for (MultiChoice multiChoice : multiChoices) {
            if (multiChoice.choices == null) continue;
            Collections.sort(multiChoice.choices);
        }
    }
}
