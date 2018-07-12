package com.elishou.anesthesiaplan.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class PlanRepository {

    private PlanDao planDao;
    private QuestionDao questionDao;
    private ChoiceDao choiceDao;

    public PlanRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        planDao = db.planDao();
        questionDao = db.questionDao();
        choiceDao = db.choiceDao();
    }

    public LiveData<List<PlanWithQuestions>> getAllPlans() {
        return planDao.getAllPlans();
    }

    public LiveData<PlanWithQuestions> getPlanWithId(String id) {
        return planDao.getPlanById(id);
    }

    public void update(List<MultiChoice> multiChoices) {
        new updateAsyncTask(questionDao, choiceDao).execute(multiChoices.toArray(new MultiChoice[multiChoices.size()]));
    }

    public void insert(PlanWithQuestions planWithQuestions) { new insertAsyncTask(planDao, questionDao, choiceDao).execute(planWithQuestions);}

    public void deletePlan(Plan plan) {
        new deleteAsyncTask(planDao).execute(plan);
    }

    private static class updateAsyncTask extends AsyncTask<MultiChoice, Void, Void> {
        private QuestionDao asyncTaskQuestionDao;
        private ChoiceDao asyncTaskChoiceDao;

        updateAsyncTask(QuestionDao questionDao, ChoiceDao choiceDao) {
            asyncTaskQuestionDao = questionDao;
            asyncTaskChoiceDao = choiceDao;
        }

        @Override
        protected Void doInBackground(MultiChoice... multiChoices) {
            for(MultiChoice multiChoice: multiChoices) {
                Question question = multiChoice.question;
                asyncTaskQuestionDao.update(question);
                List<Choice> choices = multiChoice.choices;
                if (choices == null) continue;
                for (Choice choice: choices) {
                    asyncTaskChoiceDao.update(choice);
                }
            }
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<PlanWithQuestions, Void, Void> {
        private PlanDao asyncTaskPlanDao;
        private QuestionDao asyncTaskQuestionDao;
        private ChoiceDao asyncTaskChoiceDao;

        insertAsyncTask(PlanDao planDao, QuestionDao questionDao, ChoiceDao choiceDao) {
            asyncTaskPlanDao = planDao;
            asyncTaskQuestionDao = questionDao;
            asyncTaskChoiceDao = choiceDao;
        }

        @Override
        protected Void doInBackground(PlanWithQuestions... planWithQuestions) {
            Plan plan = planWithQuestions[0].plan;
            asyncTaskPlanDao.insert(plan);
            List<MultiChoice> multiChoices = planWithQuestions[0].multiChoices;
            if (multiChoices == null) return null;
            for(MultiChoice multiChoice: multiChoices) {
                Question question = multiChoice.question;
                asyncTaskQuestionDao.insert(question);
                List<Choice> choices = multiChoice.choices;
                if (choices == null) continue;
                for (Choice choice: choices) {
                    asyncTaskChoiceDao.insert(choice);
                }
            }
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Plan, Void, Void> {
        private PlanDao asyncTaskPlanDao;

        deleteAsyncTask(PlanDao planDao) {
            asyncTaskPlanDao = planDao;
        }

        @Override
        protected Void doInBackground(Plan... plans) {
            for (Plan plan : plans) {
                asyncTaskPlanDao.delete(plan);
            }
            return null;
        }
    }

}
