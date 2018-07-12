package com.elishou.anesthesiaplan.plandetail;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.elishou.anesthesiaplan.R;
import com.elishou.anesthesiaplan.data.MultiChoice;
import com.elishou.anesthesiaplan.data.PlanWithQuestions;
import com.elishou.anesthesiaplan.utils.SnackbarMessage;
import com.elishou.anesthesiaplan.utils.SnackbarUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlanDetailFragment extends Fragment {

    public static final String ARGUMENT_PLAN_ID = "argument_plan_id";

    private PlanDetailViewModel viewModel;

    private MultiChoiceAdapter adapter;

    public PlanDetailFragment() {}

    public static PlanDetailFragment newInstance(String planId) {
        PlanDetailFragment fragment = new PlanDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_PLAN_ID, planId);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.multichoices_recyclerview)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plandetail_fragment, container, false);
        ButterKnife.bind(this, view);

        prepareViewModel();

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                viewModel.deletePlan();
                return true;
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.plandetail_fragment_menu, menu);
    }

    private void prepareViewModel() {
        viewModel = PlanDetailActivity.makeViewModel(getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        prepareSnackbar();

        prepareAdapter();

        subscribeToModel();
    }

    private void prepareSnackbar() {
        viewModel.getSnackbarMessage().observe(this, new SnackbarMessage.SnackbarObserver() {
            @Override
            public void onNewMessage(int snackbarMessageResourceId) {
                SnackbarUtils.showSnackbar(getView(), getString(snackbarMessageResourceId));
            }
        });
    }

    private void prepareAdapter() {
        adapter = new MultiChoiceAdapter(new ArrayList<MultiChoice>(0), viewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void subscribeToModel() {
        viewModel.start(getArguments().getString(ARGUMENT_PLAN_ID));
        viewModel.livePlan.observe(this, new Observer<PlanWithQuestions>() {
            @Override
            public void onChanged(@Nullable PlanWithQuestions plan) {
                if (plan == null) return;
                plan.sort();
                adapter.updateAll(plan.multiChoices);
            }
        });
    }
}
