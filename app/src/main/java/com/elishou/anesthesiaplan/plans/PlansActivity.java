package com.elishou.anesthesiaplan.plans;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.elishou.anesthesiaplan.R;
import com.elishou.anesthesiaplan.addeditplan.AddEditPlanActivity;
import com.elishou.anesthesiaplan.addeditplan.AddEditPlanFragment;
import com.elishou.anesthesiaplan.data.PlanWithQuestions;
import com.elishou.anesthesiaplan.plandetail.PlanDetailActivity;
import com.elishou.anesthesiaplan.plandetail.PlanDetailFragment;
import com.elishou.anesthesiaplan.utils.ViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlansActivity extends AppCompatActivity {

    private PlansViewModel viewModel;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plans_activity);
        ButterKnife.bind(this);

        prepareToolbar();

        prepareFragment();

        viewModel = makeViewModel(this);

        subscribeToViewModel();
    }

    private void prepareToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
    }

    private void prepareFragment() {
        PlansFragment fragment = (PlansFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            fragment = PlansFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFrame, fragment);
            transaction.commit();
        }
    }

    private void subscribeToViewModel() {
        viewModel.getNewPlanEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                addNewPlan();
            }
        });
        viewModel.getShowPlanEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String planId) {
                showPlanDetail(planId);
            }
        });
    }

    public static PlansViewModel makeViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(PlansViewModel.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        viewModel.handleActivityResult(requestCode, resultCode);
    }

    private void addNewPlan() {
        Intent intent = new Intent(this, AddEditPlanActivity.class);
        startActivityForResult(intent, AddEditPlanActivity.REQUEST_CODE);
    }

    private void showPlanDetail(String planId) {
        Intent intent = new Intent(this, PlanDetailActivity.class);
        intent.putExtra(PlanDetailFragment.ARGUMENT_PLAN_ID, planId);
        startActivityForResult(intent, AddEditPlanActivity.REQUEST_CODE);
    }
}
