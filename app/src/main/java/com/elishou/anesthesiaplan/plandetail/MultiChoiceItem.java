package com.elishou.anesthesiaplan.plandetail;

import com.elishou.anesthesiaplan.data.Choice;
import com.elishou.anesthesiaplan.data.MultiChoice;
import com.elishou.anesthesiaplan.data.Plan;

import java.util.List;

public class MultiChoiceItem {

    public int position;
    public String header;
    public String subheader;

    public MultiChoiceItem(MultiChoice multiChoice, int position) {
        this.position = position;

        this.header = multiChoice.question.problem;

        List<Choice> choices = multiChoice.choices;
        if (choices != null) {
            StringBuilder stringBuilder = new StringBuilder("");
            for (Choice choice : choices) {
                if (choice.selected) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(choice.body);
                }
            }
            this.subheader = stringBuilder.toString();
        } else {
            this.subheader = multiChoice.question.input;
        }
    }

}
