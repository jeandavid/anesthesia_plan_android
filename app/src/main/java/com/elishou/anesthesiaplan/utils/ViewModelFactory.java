package com.elishou.anesthesiaplan.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.VisibleForTesting;

import com.elishou.anesthesiaplan.addeditplan.AddEditPlanViewModel;
import com.elishou.anesthesiaplan.data.PlanRepository;
import com.elishou.anesthesiaplan.plandetail.PlanDetailViewModel;
import com.elishou.anesthesiaplan.plans.PlansViewModel;

/**
 * Ref: https://github.com/googlesamples/android-architecture/tree/dev-todo-mvvm-live
 *
 * A creator is used to inject the product ID into the ViewModel
 * <p>
 * This creator is to showcase how to inject dependencies into ViewModels. It's not
 * actually necessary in this case, as the product ID can be passed in a public method.
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static volatile ViewModelFactory INSTANCE;

    private final Application mApplication;

    private final PlanRepository planRepository;

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application, new PlanRepository(application));
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private ViewModelFactory(Application application, PlanRepository planRepository) {
        this.mApplication = application;
        this.planRepository = planRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PlansViewModel.class)) {
            //noinspection unchecked
            return (T) new PlansViewModel(mApplication, planRepository);
        } else if (modelClass.isAssignableFrom(AddEditPlanViewModel.class)) {
            //noinspection unchecked
            return (T) new AddEditPlanViewModel(mApplication, planRepository);
        } else if (modelClass.isAssignableFrom(PlanDetailViewModel.class)) {
            //noinspection unchecked
            return (T) new PlanDetailViewModel(mApplication, planRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
