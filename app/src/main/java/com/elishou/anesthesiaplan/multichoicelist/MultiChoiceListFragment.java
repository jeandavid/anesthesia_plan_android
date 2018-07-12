package com.elishou.anesthesiaplan.multichoicelist;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elishou.anesthesiaplan.R;
import com.elishou.anesthesiaplan.addeditplan.MultiChoiceListener;
import com.elishou.anesthesiaplan.data.Choice;
import com.elishou.anesthesiaplan.data.MultiChoice;
import com.elishou.anesthesiaplan.databinding.MultichoicelistFragmentBinding;

import org.parceler.Parcels;

import java.util.ArrayList;

public class MultiChoiceListFragment extends Fragment {

    public static final String ARGUMENT_QUESTION = "argument_question";
    public static final String ARGUMENT_POSITION = "argument_position";
    public static final String ARGUMENT_TOTAL = "argument_total";

    public MultiChoiceListFragment() {}

    public static MultiChoiceListFragment newInstance(MultiChoice multiChoice, int position, int numberOfQuestions) {
        MultiChoiceListFragment fragment = new MultiChoiceListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_QUESTION, Parcels.wrap(multiChoice));
        args.putInt(ARGUMENT_POSITION, position);
        args.putInt(ARGUMENT_TOTAL, numberOfQuestions);
        fragment.setArguments(args);
        return fragment;
    }

    private MultiChoiceListener listener;

    private MultiChoiceListViewModel viewModel;

    private MultichoicelistFragmentBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MultiChoiceListener) {
            listener = (MultiChoiceListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.multichoicelist_fragment, container, false);
        binding = MultichoicelistFragmentBinding.bind(view);

        viewModel = makeViewModel(getActivity());
        subscribeToModel();

        binding.setViewmodel(viewModel);

        return view;
    }

    @Nullable
    private MultiChoiceListViewModel makeViewModel(FragmentActivity activity) {
        if (getArguments() == null) return null;
        Parcelable wrappedQuestion = getArguments().getParcelable(ARGUMENT_QUESTION);
        MultiChoice multiChoice = Parcels.unwrap(wrappedQuestion);
        if (multiChoice == null) return null;
        int position = getArguments().getInt(ARGUMENT_POSITION);
        int numberOfQuestions = getArguments().getInt(ARGUMENT_TOTAL);
        if (getActivity() == null) return null;
        Application application = getActivity().getApplication();
        MultiChoiceListViewModel.Factory factory = new MultiChoiceListViewModel.Factory(application, multiChoice, position, numberOfQuestions);
        return ViewModelProviders.of(this, factory).get(MultiChoiceListViewModel.class);
    }

    private void subscribeToModel() {
        viewModel.getLiveMultiChoice().observe(this, new Observer<MultiChoice>() {
            @Override
            public void onChanged(@Nullable MultiChoice multiChoice) {
                if (listener != null) {
                    listener.onMultiChoiceChange(multiChoice);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = binding.choicesList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ChoicesAdapter adapter = new ChoicesAdapter(new ArrayList<Choice>(0), viewModel);
        recyclerView.setAdapter(adapter);
    }
}
