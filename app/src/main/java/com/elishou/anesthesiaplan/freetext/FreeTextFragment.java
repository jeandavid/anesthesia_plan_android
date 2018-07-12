package com.elishou.anesthesiaplan.freetext;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elishou.anesthesiaplan.R;
import com.elishou.anesthesiaplan.addeditplan.QuestionListener;
import com.elishou.anesthesiaplan.data.Question;
import com.elishou.anesthesiaplan.databinding.FreetextFragmentBinding;

import org.parceler.Parcels;

public class FreeTextFragment extends Fragment {

    public static final String TAG = FreeTextFragment.class.getSimpleName();

    public static final String ARGUMENT_QUESTION = "argument_question";
    public static final String ARGUMENT_POSITION = "argument_position";
    public static final String ARGUMENT_TOTAL = "argument_total";

    public FreeTextFragment() {}

    public static FreeTextFragment newInstance(Question question, int position, int numberOfQuestions) {
        FreeTextFragment fragment = new FreeTextFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_QUESTION, Parcels.wrap(question));
        args.putInt(ARGUMENT_POSITION, position);
        args.putInt(ARGUMENT_TOTAL, numberOfQuestions);
        fragment.setArguments(args);
        return fragment;
    }

    private FreeTextViewModel viewModel;

    private QuestionListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof QuestionListener) {
            listener = (QuestionListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.freetext_fragment, container, false);
        FreetextFragmentBinding binding = FreetextFragmentBinding.bind(view);

        viewModel = makeViewModel(getActivity());
        subscribeToModel();

        binding.setViewmodel(viewModel);

        return view;
    }

    @Nullable
    private FreeTextViewModel makeViewModel(FragmentActivity activity) {
        if (getArguments() == null) return null;
        Parcelable wrappedQuestion = getArguments().getParcelable(ARGUMENT_QUESTION);
        Question question = Parcels.unwrap(wrappedQuestion);
        if (question == null) return null;
        int position = getArguments().getInt(ARGUMENT_POSITION);
        int numberOfQuestions = getArguments().getInt(ARGUMENT_TOTAL);
        if (getActivity() == null) return null;
        Application application = activity.getApplication();
        FreeTextViewModel.Factory factory = new FreeTextViewModel.Factory(application, question, position, numberOfQuestions);
        return ViewModelProviders.of(this, factory).get(FreeTextViewModel.class);
    }

    private void subscribeToModel() {
        viewModel.input.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String input) {
                if (listener != null) {
                    Question question = viewModel.getQuestion();
                    question.input = input;
                    listener.onQuestionChange(question);
                }
            }
        });
    }
}
