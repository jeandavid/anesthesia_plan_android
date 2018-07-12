package com.elishou.anesthesiaplan.plandetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.elishou.anesthesiaplan.data.MultiChoice;
import com.elishou.anesthesiaplan.databinding.MultichoiceItemBinding;

import java.util.List;

public class MultiChoiceAdapter extends RecyclerView.Adapter<MultiChoiceAdapter.ViewHolder>{

    private final PlanDetailViewModel viewModel;

    private List<MultiChoice> multiChoices;

    public MultiChoiceAdapter(List<MultiChoice> multiChoices, PlanDetailViewModel viewModel){
        setList(multiChoices);
        this.viewModel = viewModel;
    }

    public void updateAll(List<MultiChoice> multiChoices) {setList(multiChoices);}

    private void setList(List<MultiChoice> multiChoices) {
        this.multiChoices = multiChoices;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return multiChoices != null ? multiChoices.size() : 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MultichoiceItemBinding binding = MultichoiceItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MultiChoice multiChoice = multiChoices.get(position);
        MultiChoiceItem item = new MultiChoiceItem(multiChoice, position);
        holder.bind(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final MultichoiceItemBinding binding;
        private final PlanDetailViewModel viewModel;

        public ViewHolder(MultichoiceItemBinding binding, PlanDetailViewModel viewModel) {
            super(binding.getRoot());
            this.binding = binding;
            this.viewModel = viewModel;
        }

        public void bind(MultiChoiceItem item) {
            binding.setItem(item);
            binding.setListener(new MultiChoiceItemListener() {
                @Override
                public void onMultiChoiceSelected(int position) {
                    viewModel.getEditPlanEvent().setValue(position);
                }
            });
            binding.executePendingBindings();
        }
    }

}
