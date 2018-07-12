package com.elishou.anesthesiaplan.plandetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.elishou.anesthesiaplan.R;
import com.elishou.anesthesiaplan.addeditplan.AddEditPlanActivity;
import com.elishou.anesthesiaplan.data.PlanRepository;
import com.elishou.anesthesiaplan.data.PlanWithQuestions;
import com.elishou.anesthesiaplan.utils.SingleLiveEvent;
import com.elishou.anesthesiaplan.utils.SnackbarMessage;

public class PlanDetailViewModel extends AndroidViewModel {

    private final PlanRepository planRepository;

    public LiveData<PlanWithQuestions> livePlan;

    private String planId;

    private final SnackbarMessage snackbarMessage = new SnackbarMessage();

    private final SingleLiveEvent<Void> planDeleted = new SingleLiveEvent<>();

    private final SingleLiveEvent<Integer> editPlanEvent = new SingleLiveEvent<>();

    public PlanDetailViewModel(Application context, PlanRepository planRepository) {
        super(context);
        this.planRepository = planRepository;
    }

    public void start(@NonNull String planId) {
        this.planId = planId;
        livePlan = planRepository.getPlanWithId(planId);
    }

    public String getPlanId() {
        return planId;
    }

    public void deletePlan() {
        if (livePlan.getValue() != null && livePlan.getValue().plan != null) {
            planRepository.deletePlan(livePlan.getValue().plan);
            planDeleted.call();
        }
    }

    public SnackbarMessage getSnackbarMessage() {return snackbarMessage;}

    public SingleLiveEvent<Void> getPlanDeletedEvent() {
        return planDeleted;
    }

    public SingleLiveEvent<Integer> getEditPlanEvent() {
        return editPlanEvent;
    }

    public void handleActivityResult(int requestCode, int resultCode) {
        if (AddEditPlanActivity.REQUEST_CODE == requestCode) {
            switch (resultCode) {
                case AddEditPlanActivity.EDIT_RESULT_OK:
                    snackbarMessage.setValue(R.string.successfully_updated_plan_message);
                    break;
            }
        }
    }
}



































