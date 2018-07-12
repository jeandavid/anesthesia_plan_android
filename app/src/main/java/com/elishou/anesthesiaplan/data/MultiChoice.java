package com.elishou.anesthesiaplan.data;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Relation;
import android.support.annotation.NonNull;

import org.parceler.Parcel;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Parcel
public class MultiChoice implements Comparable<MultiChoice> {

    @Embedded
    public Question question;

    @Relation(parentColumn = Question.COLUMN_ID, entityColumn = Choice.COLUMN_QUESTION_ID, entity = Choice.class)
    public List<Choice> choices;

    public static final int MULTI_CHOICE = 0, FREE_TEXT = 1;

    public int getQuestionType() {
        return (this.choices == null || this.choices.isEmpty()) ? FREE_TEXT : MULTI_CHOICE;
    }

    public MultiChoice() {}

    @Ignore
    public MultiChoice(Question question) {
        this.question = question;
    }

    public MultiChoice(@NonNull String planId, int step) {
        switch (step) {
            case 0:
                this.question = new Question("איזה סוג הרדמה?", planId, step);
                this.choices = Arrays.asList(
                        new Choice("כללית", 0, this.question.id),
                        new Choice("אזורית", 1, this.question.id),
                        new Choice("בלוק", 2, this.question.id),
                        new Choice("סדציה", 3, this.question.id));
                break;
            case 1:
                this.question = new Question("איזה ניטורים בנוסף ל-ASA standard?", planId, step);
                this.choices = Arrays.asList(
                        new Choice("5 lead ECG", 0, this.question.id),
                        new Choice("Arterial line", 1, this.question.id),
                        new Choice("קטטר שתן", 2, this.question.id),
                        new Choice("זונדה", 3, this.question.id),
                        new Choice("CVP", 4, this.question.id),
                        new Choice("BIS", 5, this.question.id),
                        new Choice("Vigileo", 6, this.question.id),
                        new Choice("NIRS", 7, this.question.id),
                        new Choice("Nerve stimulator", 8, this.question.id),
                        new Choice("TEE", 9, this.question.id));
                break;
            case 2:
                this.question = new Question("האם צריך מנות דם?", planId, step);
                this.choices = Arrays.asList(
                        new Choice("לא", 0, this.question.id),
                        new Choice("רק סוג וסקר", 1, this.question.id),
                        new Choice("מנות מוכנות בבנק הדם", 2, this.question.id),
                        new Choice("מנות מוכנות בחדר ניתוח", 3, this.question.id),
                        new Choice("cell saver", 4, this.question.id));
                break;
            case 3:
                this.question = new Question("איזה נוזלים?", planId, step);
                this.choices = Arrays.asList(
                        new Choice("Ringer Lactate", 0, this.question.id),
                        new Choice("Plasmalyte", 1, this.question.id),
                        new Choice("0.9% NaCl", 2, this.question.id),
                        new Choice("אחר", 3, this.question.id));
                break;
            case 4:
                this.question = new Question("חימום נוזלים?", planId, step);
                this.choices = Arrays.asList(
                        new Choice("ללא", 0, this.question.id),
                        new Choice("חימום רגיל", 1, this.question.id),
                        new Choice("Hotline", 2, this.question.id),
                        new Choice("Level 1", 3, this.question.id));
                break;
            case 5:
                this.question = new Question("האם יש אינדיקציה ל-RSI?", planId, step);
                this.choices = Arrays.asList(
                        new Choice("לא", 0, this.question.id),
                        new Choice("כן", 1, this.question.id));
                break;
            case 6:
                this.question = new Question("האם יש חשד ל-difficult airway?", planId, step);
                this.choices = Arrays.asList(
                        new Choice("לא", 0, this.question.id),
                        new Choice("כן, נשתמש ב-video laryngoscope", 1, this.question.id),
                        new Choice("כן, נשתמש ב-fiberoptic", 2, this.question.id),
                        new Choice("כן, נשתמש בשיטה אחרת", 3, this.question.id));
                break;
            case 7:
                this.question = new Question("ניהול נתיב אוויר?", planId, step);
                this.choices = Arrays.asList(
                        new Choice("טובוס רגיל", 0, this.question.id),
                        new Choice("LMA", 1, this.question.id),
                        new Choice("מסכה", 2, this.question.id),
                        new Choice("טובוס נזאלי", 3, this.question.id),
                        new Choice("טובוס מיוחד אחר", 4, this.question.id));
                break;
            case 8:
                this.question = new Question("מה ה-Maintenance של ההרדמה?", planId, step);
                this.choices = Arrays.asList(
                        new Choice("גזים", 0, this.question.id),
                        new Choice("TIVA", 1, this.question.id));
                break;
            case 9:
                this.question = new Question("האם החולה לאקסטובציה?", planId, step);
                this.choices = Arrays.asList(
                        new Choice("כן, awake", 0, this.question.id),
                        new Choice("כן, deep", 1, this.question.id),
                        new Choice("לא", 2, this.question.id));
                break;
            case 10:
                this.question = new Question("טיפול בכאב post-op?", planId, step);
                this.choices = Arrays.asList(
                        new Choice("IV PRN", 0, this.question.id),
                        new Choice("IV PCA", 1, this.question.id),
                        new Choice("PECA", 2, this.question.id),
                        new Choice("peripheral block", 3, this.question.id));
                break;
            case 11:
                this.question = new Question("לאן החולה הולך בסוף?", planId, step);
                this.choices = Arrays.asList(
                        new Choice("התעוררות והביתה או למחלקה", 0, this.question.id),
                        new Choice("כירורגית מוגברת", 1, this.question.id),
                        new Choice("טיפול נמרץ כללי", 2, this.question.id),
                        new Choice("טיפול נמרץ אחר", 3, this.question.id));
                break;
            case 12:
                this.question = new Question("משהו אחר חשוב?", planId, step);
                break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiChoice multiChoice = (MultiChoice) o;
        return Objects.equals(multiChoice.question, question);
    }

    @Override
    public int hashCode() {
        return question.id.hashCode();
    }

    @Override
    public int compareTo(@NonNull MultiChoice o) {
        return Integer.compare(question.step, o.question.step);
    }
}
