package com.elishou.anesthesiaplan.addeditplan;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.elishou.anesthesiaplan.data.MultiChoice;
import com.elishou.anesthesiaplan.data.PlanWithQuestions;
import com.elishou.anesthesiaplan.data.Question;
import com.elishou.anesthesiaplan.freetext.FreeTextFragment;
import com.elishou.anesthesiaplan.multichoicelist.MultiChoiceListFragment;

public class QuestionPagerAdapter extends FragmentPagerAdapter {

    private PlanWithQuestions plan;

    public QuestionPagerAdapter(FragmentManager fragmentManager, PlanWithQuestions plan) {
        super(fragmentManager);
        this.plan = plan;
    }

    @Override
    public Fragment getItem(int position) {
        int numberOfQuestions = plan.getNumberOfQuestions();
        MultiChoice multiChoice = plan.multiChoices.get(position);
        switch (multiChoice.getQuestionType()) {
            case MultiChoice.MULTI_CHOICE:
                return MultiChoiceListFragment.newInstance(multiChoice, position, numberOfQuestions);
            case MultiChoice.FREE_TEXT:
                Question question = multiChoice.question;
                return FreeTextFragment.newInstance(question, position, numberOfQuestions);
        }
        return null;
    }

    @Override
    public int getCount() {
        return plan.getNumberOfQuestions();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "QUESTION " + (position + 1);
    }
}
