package com.elishou.anesthesiaplan.addeditplan;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.elishou.anesthesiaplan.R;
import com.elishou.anesthesiaplan.data.Plan;
import com.elishou.anesthesiaplan.data.PlanWithQuestions;
import com.elishou.anesthesiaplan.utils.SnackbarMessage;
import com.elishou.anesthesiaplan.utils.SnackbarUtils;


import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class AddEditPlanFragment extends Fragment {

    public static final String ARGUMENT_EDIT_PLAN_ID = "EDIT_PLAN_ID";
    public static final String ARGUMENT_START_POSITION = "START_POSITION";

    private AddEditPlanViewModel viewModel;

    private QuestionPagerAdapter pagerAdapter;

    public AddEditPlanFragment() {}

    public static AddEditPlanFragment newInstance() {return new AddEditPlanFragment();}

    // TODO DataBinding
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    // TODO DataBinding
    @BindView(R.id.indicator)
    CircleIndicator indicator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addeditplan_fragment, container, false);
        ButterKnife.bind(this, view);

        viewModel = AddEditPlanActivity.makeViewModel(getActivity());

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_create:
                viewModel.savePlan();
                return true;
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.addeditplan_fragment_menu, menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        prepareSnackbar();

        prepareActionBar();

        loadPlan();
    }

    private void loadPlan() {
        if (getArguments() != null && getArguments().getString(ARGUMENT_EDIT_PLAN_ID) != null) {
            viewModel.start(getArguments().getString(ARGUMENT_EDIT_PLAN_ID));
            subscribeToModel();
        } else {
            PlanWithQuestions planWithQuestions = new PlanWithQuestions();
            viewModel.start(planWithQuestions);
            prepareViewPager(planWithQuestions);
        }
    }

    private void subscribeToModel() {
        viewModel.livePlan.observe(this, new Observer<PlanWithQuestions>() {
            @Override
            public void onChanged(@Nullable PlanWithQuestions plan) {
                if (plan == null) return;
                plan.sort();
                prepareViewPager(plan);
            }
        });
    }

    private void prepareViewPager(PlanWithQuestions plan) {
        if (getActivity() == null ) return;
        pagerAdapter = new QuestionPagerAdapter(getActivity().getSupportFragmentManager(), plan);
        viewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewPager);
        if (getArguments() != null) {
            viewPager.setCurrentItem(getArguments().getInt(ARGUMENT_START_POSITION, 0));
        }
    }

    private void prepareSnackbar() {
        viewModel.getSnackbarMessage().observe(this, new SnackbarMessage.SnackbarObserver() {
            @Override
            public void onNewMessage(int snackbarMessageResourceId) {
                SnackbarUtils.showSnackbar(getView(), getString(snackbarMessageResourceId));
            }
        });
    }

    private void prepareActionBar() {
        if (getActivity() == null || ((AppCompatActivity)getActivity()).getSupportActionBar() == null) return;
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar == null) {
            return;
        }
        if (getArguments() != null && getArguments().getString(ARGUMENT_EDIT_PLAN_ID) != null) {
            actionBar.setTitle(R.string.edit_plan_title);
        } else {
            actionBar.setTitle(R.string.add_plan_title);
        }
    }
}
