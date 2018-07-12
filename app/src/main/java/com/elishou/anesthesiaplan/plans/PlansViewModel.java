package com.elishou.anesthesiaplan.plans;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.elishou.anesthesiaplan.R;
import com.elishou.anesthesiaplan.addeditplan.AddEditPlanActivity;
import com.elishou.anesthesiaplan.data.Plan;
import com.elishou.anesthesiaplan.data.PlanRepository;
import com.elishou.anesthesiaplan.data.PlanWithQuestions;
import com.elishou.anesthesiaplan.plandetail.PlanDetailActivity;
import com.elishou.anesthesiaplan.utils.SingleLiveEvent;
import com.elishou.anesthesiaplan.utils.SnackbarMessage;

import java.util.List;

public class PlansViewModel extends AndroidViewModel {

    private final SnackbarMessage snackbarMessage;

    private final SingleLiveEvent<String> showPlanEvent;

    private final SingleLiveEvent<Void> newPlanEvent;

    private final PlanRepository planRepository;

    private LiveData<List<PlanWithQuestions>> plans;

    public PlansViewModel(Application context, PlanRepository repository) {
        super(context);
        snackbarMessage = new SnackbarMessage();
        showPlanEvent = new SingleLiveEvent<>();
        newPlanEvent = new SingleLiveEvent<>();
        planRepository = repository;
        plans = planRepository.getAllPlans();
    }

    public LiveData<List<PlanWithQuestions>> getAllPlans() {
        return plans;
    }

    SnackbarMessage getSnackbarMessage() {return snackbarMessage;}

    SingleLiveEvent<Void> getNewPlanEvent() {return newPlanEvent;}

    SingleLiveEvent<String> getShowPlanEvent() {return showPlanEvent;}

    public void addNewPLan() {newPlanEvent.call();}

    public void handleActivityResult(int requestCode, int resultCode) {
        if (AddEditPlanActivity.REQUEST_CODE == requestCode) {
            switch (resultCode) {
                case AddEditPlanActivity.ADD_RESULT_OK:
                    snackbarMessage.setValue(R.string.successfully_added_plan_message);
                    break;
                case AddEditPlanActivity.EDIT_RESULT_OK:
                    snackbarMessage.setValue(R.string.successfully_updated_plan_message);
                    break;
                case PlanDetailActivity.DELETE_RESULT_OK:
                    snackbarMessage.setValue(R.string.successfully_deleted_plan_message);
                    break;
            }
        }
    }
}
