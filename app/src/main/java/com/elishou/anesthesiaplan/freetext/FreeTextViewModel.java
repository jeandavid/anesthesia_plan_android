package com.elishou.anesthesiaplan.freetext;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.elishou.anesthesiaplan.R;
import com.elishou.anesthesiaplan.data.Question;

public class FreeTextViewModel extends AndroidViewModel {

    private final Question question;

    public final MutableLiveData<String> input = new MutableLiveData<>();

    public final ObservableField<String> problem = new ObservableField<>();

    public final ObservableField<String> stepCounter = new ObservableField<>();

    public FreeTextViewModel(Application context, Question question, int position, int numberOfQuestions) {
        super(context);
        this.question = question;
        this.problem.set(question.problem);
        this.input.setValue(question.input);
        this.stepCounter.set(context.getString(R.string.question_step_counter, position + 1, numberOfQuestions));
    }

    public Question getQuestion() {
        return question;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Question question;

        private final int position;

        private final int numberOfQuestions;

        private final Application application;

        public Factory(Application application, @NonNull Question question, int position, int numberOfQuestions) {
            this.application = application;
            this.question = question;
            this.position = position;
            this.numberOfQuestions = numberOfQuestions;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(FreeTextViewModel.class)) {
                //noinspection unchecked
                return (T) new FreeTextViewModel(application, question, position, numberOfQuestions);
            }
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }
}
