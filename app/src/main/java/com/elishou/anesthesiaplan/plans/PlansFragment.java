package com.elishou.anesthesiaplan.plans;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.elishou.anesthesiaplan.R;
import com.elishou.anesthesiaplan.data.PlanWithQuestions;
import com.elishou.anesthesiaplan.utils.SnackbarMessage;
import com.elishou.anesthesiaplan.utils.SnackbarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlansFragment extends Fragment {

    private PlansViewModel viewModel;

    private PlansAdapter adapter;

    public PlansFragment() {}

    public static PlansFragment newInstance() {return new PlansFragment();}

    @BindView(R.id.plans_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.noPlans)
    LinearLayout noPlansLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plans_fragment, container, false);
        ButterKnife.bind(this, view);

        prepareViewModel();

        return view;
    }

    private void prepareViewModel() {
        viewModel = PlansActivity.makeViewModel(getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        prepareSnackbar();

        prepareFab();

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

    private void prepareFab() {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_plan);
        fab.setImageResource(R.drawable.ic_add_white_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addNewPLan();
            }
        });
    }

    private void prepareAdapter() {
        adapter = new PlansAdapter(new ArrayList<PlanWithQuestions>(0), viewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void subscribeToModel() {
        viewModel.getAllPlans().observe(this, new Observer<List<PlanWithQuestions>>() {
            @Override
            public void onChanged(@Nullable List<PlanWithQuestions> planWithQuestions) {
                adapter.updateAll(planWithQuestions);
                Boolean isEmpty = planWithQuestions.isEmpty();
                recyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
                noPlansLayout.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            }
        });
    }
}
















































