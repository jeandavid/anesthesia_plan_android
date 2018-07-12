package com.elishou.anesthesiaplan.addeditplan;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.elishou.anesthesiaplan.data.MultiChoice;
import com.elishou.anesthesiaplan.data.PlanRepository;
import com.elishou.anesthesiaplan.data.PlanWithQuestions;
import com.elishou.anesthesiaplan.data.Question;
import com.elishou.anesthesiaplan.utils.SingleLiveEvent;
import com.elishou.anesthesiaplan.utils.SnackbarMessage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AddEditPlanViewModel extends AndroidViewModel {

    private final PlanRepository planRepository;

    private PlanWithQuestions planWithQuestions;

    private final Set<MultiChoice> multiChoices = new HashSet<>();

    public LiveData<PlanWithQuestions> livePlan;

    @Nullable
    private String planId;

    private boolean isNewPlan;

    private final SnackbarMessage snackbarMessage = new SnackbarMessage();

    private final SingleLiveEvent<Void> planUpdated = new SingleLiveEvent<>();

    private final SingleLiveEvent<Void> planAdded = new SingleLiveEvent<>();

    public AddEditPlanViewModel(Application context, PlanRepository planRepository) {
        super(context);
        this.planRepository = planRepository;
    }

    public void start(@NonNull PlanWithQuestions planWithQuestions) {
        this.isNewPlan = true;
        this.planWithQuestions = planWithQuestions;
        this.multiChoices.addAll(planWithQuestions.multiChoices);
    }

    public void start(@NonNull String planId) {
        this.planId = planId;
        this.isNewPlan = false;
        this.livePlan = this.planRepository.getPlanWithId(planId);
    }

    public void savePlan() {
        if (isNewPlan || planId == null) {
            createPlan();
        } else {
            updatePlan();
        }
    }

    public SnackbarMessage getSnackbarMessage() {return snackbarMessage;}

    public SingleLiveEvent<Void> getPlanUpdatedEvent() {return planUpdated;}

    public SingleLiveEvent<Void> getPlanAddedEvent() {return planAdded;}

    private void createPlan() {
        planWithQuestions.multiChoices = new ArrayList<>(multiChoices);
        planRepository.insert(planWithQuestions);
        planAdded.call();
    }

    private void updatePlan() {
        if (!multiChoices.isEmpty()) {
           planRepository.update(new ArrayList<>(multiChoices));
        }
        planUpdated.call();
    }

    public void setQuestion(Question question) {
        MultiChoice multiChoice = new MultiChoice(question);
        multiChoices.remove(multiChoice);
        multiChoices.add(multiChoice);
    }

    public void setMultiChoice(MultiChoice multiChoice) {
        multiChoices.remove(multiChoice);
        multiChoices.add(multiChoice);
    }
}

























