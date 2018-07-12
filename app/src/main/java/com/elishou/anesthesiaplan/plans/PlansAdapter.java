package com.elishou.anesthesiaplan.plans;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elishou.anesthesiaplan.R;
import com.elishou.anesthesiaplan.data.PlanWithQuestions;
import com.elishou.anesthesiaplan.databinding.PlanItemBinding;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.ViewHolder> {

    private final PlansViewModel plansViewModel;

    private List<PlanWithQuestions> plans;

    public PlansAdapter(List<PlanWithQuestions> plans, PlansViewModel plansViewModel) {
        setList(plans);
        this.plansViewModel = plansViewModel;
    }

    public void updateAll(List<PlanWithQuestions> plans) {
        setList(plans);
    }

    private void setList(List<PlanWithQuestions> plans) {
        if (plans == null) return;
        for (PlanWithQuestions plan : plans) {
            plan.sort();
        }
        this.plans = plans;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return plans != null ?  plans.size() : 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PlanItemBinding binding = PlanItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding, plansViewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlanWithQuestions planWithQuestions = plans.get(position);
        PlanItem planItem = new PlanItem(planWithQuestions);
        holder.bind(planItem);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final PlanItemBinding binding;
        private final PlansViewModel viewModel;

        public ViewHolder(PlanItemBinding binding, PlansViewModel viewModel) {
            super(binding.getRoot());
            this.binding = binding;
            this.viewModel = viewModel;
        }

        public void bind(PlanItem planItem) {
            binding.setPlan(planItem);
            binding.setListener(new PlanItemListener() {
                @Override
                public void onPlanSelected(String id) {
                    viewModel.getShowPlanEvent().setValue(id);
                }
            });
            binding.executePendingBindings();
        }
    }
}



































