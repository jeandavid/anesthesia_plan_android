package com.elishou.anesthesiaplan.plandetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.elishou.anesthesiaplan.R;
import com.elishou.anesthesiaplan.addeditplan.AddEditPlanActivity;
import com.elishou.anesthesiaplan.addeditplan.AddEditPlanFragment;
import com.elishou.anesthesiaplan.utils.ViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlanDetailActivity extends AppCompatActivity {

    public static final int DELETE_RESULT_OK = RESULT_FIRST_USER + 2;

    private PlanDetailViewModel viewModel;

    @BindView(R.id.detailToolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plandetail_activity);
        ButterKnife.bind(this);

        prepareToolbar();

        prepareFragment();

        viewModel = makeViewModel(this);

        subscribeToViewModel();
    }

    private void prepareToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.plan_detail_title);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        viewModel.handleActivityResult(requestCode, resultCode);
    }

    private void prepareFragment() {
        String planId = getIntent().getStringExtra(PlanDetailFragment.ARGUMENT_PLAN_ID);
        PlanDetailFragment fragment = (PlanDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            fragment = PlanDetailFragment.newInstance(planId);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFrame, fragment);
            transaction.commit();
        }
    }

    public static PlanDetailViewModel makeViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(PlanDetailViewModel.class);
    }

    private void subscribeToViewModel() {
        viewModel.getEditPlanEvent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer position) {
                showEditPlan(viewModel.getPlanId(), position);
            }
        });
        viewModel.getPlanDeletedEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                setResult(DELETE_RESULT_OK);
                finish();
            }
        });
    }

    private void showEditPlan(String planId, int position) {
        Intent intent = new Intent(this, AddEditPlanActivity.class);
        intent.putExtra(AddEditPlanFragment.ARGUMENT_EDIT_PLAN_ID, planId);
        intent.putExtra(AddEditPlanFragment.ARGUMENT_START_POSITION, position);
        startActivityForResult(intent, AddEditPlanActivity.REQUEST_CODE);
    }
}





































