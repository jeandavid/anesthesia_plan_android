package com.elishou.anesthesiaplan.multichoicelist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.elishou.anesthesiaplan.R;
import com.elishou.anesthesiaplan.data.Choice;
import com.elishou.anesthesiaplan.data.MultiChoice;
import com.elishou.anesthesiaplan.data.Question;
import com.elishou.anesthesiaplan.utils.SingleLiveEvent;

import java.util.List;

public class MultiChoiceListViewModel extends AndroidViewModel {

    private final MultiChoice multiChoice;

    private final MutableLiveData<MultiChoice> liveMultiChoice = new MutableLiveData<>();

    public final ObservableList<Choice> choices = new ObservableArrayList<>();

    public final ObservableField<String> problem = new ObservableField<>();

    public final ObservableField<String> stepCounter = new ObservableField<>();

    public MultiChoiceListViewModel(Application context, MultiChoice multiChoice, int position, int numberOfQuestions) {
        super(context);
        this.multiChoice = multiChoice;
        this.problem.set(multiChoice.question.problem);
        this.choices.addAll(multiChoice.choices);
        this.stepCounter.set(context.getString(R.string.question_step_counter, position + 1, numberOfQuestions));
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final MultiChoice multiChoice;

        private final int position;

        private final int numberOfQuestions;

        private final Application application;

        public Factory(Application application, @NonNull MultiChoice multiChoice, int position, int numberOfQuestions) {
            this.application = application;
            this.multiChoice = multiChoice;
            this.position = position;
            this.numberOfQuestions = numberOfQuestions;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MultiChoiceListViewModel.class)) {
                //noinspection unchecked
                return (T) new MultiChoiceListViewModel(application, multiChoice, position, numberOfQuestions);
            }
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }

    public void toggleChoice(int index, Choice choice) {
        choice.selected = !choice.selected;
        this.choices.set(index, choice);
        this.multiChoice.choices = this.choices;
        this.liveMultiChoice.setValue(this.multiChoice);
    }

    public LiveData<MultiChoice> getLiveMultiChoice() {
        return liveMultiChoice;
    }
}
