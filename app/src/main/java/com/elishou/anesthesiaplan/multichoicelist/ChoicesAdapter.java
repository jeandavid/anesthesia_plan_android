package com.elishou.anesthesiaplan.multichoicelist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.elishou.anesthesiaplan.data.Choice;
import com.elishou.anesthesiaplan.databinding.ChoiceItemBinding;

import java.util.List;

public class ChoicesAdapter extends RecyclerView.Adapter<ChoicesAdapter.ViewHolder> {

    private final MultiChoiceListViewModel viewModel;

    private List<Choice> choices;

    public ChoicesAdapter(List<Choice> choices, MultiChoiceListViewModel viewModel) {
        setList(choices);
        this.viewModel = viewModel;
    }

    public void replaceData(List<Choice> choices) {
        setList(choices);
    }

    private void setList(List<Choice> choices) {
        this.choices = choices;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return choices != null ? choices.size() : 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ChoiceItemBinding binding = ChoiceItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(choices.get(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ChoiceItemBinding binding;
        private final MultiChoiceListViewModel viewModel;

        public ViewHolder(ChoiceItemBinding binding, MultiChoiceListViewModel viewModel) {
            super(binding.getRoot());
            this.binding = binding;
            this.viewModel = viewModel;
        }

        public void bind(Choice choice) {
            binding.setChoice(choice);
            binding.setListener(new ChoiceItemListener() {
                @Override
                public void onChoiceSelected(Choice choice) {
                    viewModel.toggleChoice(getAdapterPosition(), choice);
                }
            });
            binding.executePendingBindings();
        }
    }

}
