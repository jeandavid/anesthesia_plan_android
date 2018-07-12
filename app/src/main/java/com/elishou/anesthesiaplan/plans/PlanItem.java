package com.elishou.anesthesiaplan.plans;

import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;

import com.elishou.anesthesiaplan.data.Choice;
import com.elishou.anesthesiaplan.data.MultiChoice;
import com.elishou.anesthesiaplan.data.PlanWithQuestions;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlanItem {

    public String id;
    public String header;
    public String subheader;
    public String dateStr;

    public PlanItem(PlanWithQuestions plan) {
        this.id = plan.plan.id;
        List<MultiChoice> multiChoices = plan.multiChoices;
        if (multiChoices == null || multiChoices.size() < 2) return;

        // prepare header
        MultiChoice first = multiChoices.get(0);
        List<Choice> choices = first.choices;
        this.header = "";
        if (choices != null) {
            for (Choice choice : choices) {
                if (choice.selected) {
                    this.header = choice.body;
                    break;
                }
            }
        }

        // prepare subheader
        MultiChoice second = multiChoices.get(1);
        choices = second.choices;
        StringBuilder stringBuilder = new StringBuilder("");
        if (choices != null) {
            for (Choice choice : choices) {
                if (choice.selected) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(choice.body);
                }
            }
        }
        this.subheader = stringBuilder.toString();

        // prepare date
        Calendar planCal = Calendar.getInstance();
        planCal.setTime(plan.plan.createdAt);
        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.YEAR) != planCal.get(Calendar.YEAR)) {
            this.dateStr = DateFormat.format("MMM yyyy", planCal).toString();
        } else if (now.get(Calendar.MONTH) != planCal.get(Calendar.MONTH)) {
            this.dateStr = DateFormat.format("d MMM", planCal).toString();
        } else if (now.get(Calendar.DATE) == planCal.get(Calendar.DATE)) {
            this.dateStr = "Today, " + DateFormat.format("HH:mm", planCal).toString();
        } else if (now.get(Calendar.DATE) - planCal.get(Calendar.DATE) == 1){
            this.dateStr = "Yesterday, " + DateFormat.format("HH:mm", planCal).toString();
        } else {
            this.dateStr = DateFormat.format("d MMM", planCal).toString();
        }
    }

}
