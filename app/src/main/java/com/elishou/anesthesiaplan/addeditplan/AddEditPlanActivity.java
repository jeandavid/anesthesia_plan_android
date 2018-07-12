package com.elishou.anesthesiaplan.addeditplan;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.elishou.anesthesiaplan.R;
import com.elishou.anesthesiaplan.data.MultiChoice;
import com.elishou.anesthesiaplan.data.Question;
import com.elishou.anesthesiaplan.utils.ViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditPlanActivity extends AppCompatActivity implements QuestionListener, MultiChoiceListener {

    public static final int REQUEST_CODE = 1;

    public static final int ADD_RESULT_OK = RESULT_FIRST_USER + 1;

    public static final int EDIT_RESULT_OK = RESULT_FIRST_USER + 3;

    @BindView(R.id.editToolbar)
    Toolbar mToolbar;

    private AddEditPlanViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addeditplan_activity);
        ButterKnife.bind(this);

        prepareToolbar();

        prepareFragment();

        subscribeToViewModel();
    }

    private void prepareToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_clear_white_24dp);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void prepareFragment() {
        AddEditPlanFragment fragment = (AddEditPlanFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            fragment = AddEditPlanFragment.newInstance();

            Bundle bundle = new Bundle();
            if (getIntent().getStringExtra(AddEditPlanFragment.ARGUMENT_EDIT_PLAN_ID) != null) {
                bundle.putString(AddEditPlanFragment.ARGUMENT_EDIT_PLAN_ID,
                            getIntent().getStringExtra(AddEditPlanFragment.ARGUMENT_EDIT_PLAN_ID));
            }
            bundle.putInt(AddEditPlanFragment.ARGUMENT_START_POSITION,
                    getIntent().getIntExtra(AddEditPlanFragment.ARGUMENT_START_POSITION, 0));
            fragment.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFrame, fragment);
            transaction.commit();
        }
    }

    private void subscribeToViewModel() {
        viewModel = makeViewModel(this);
        viewModel.getPlanUpdatedEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                setResult(EDIT_RESULT_OK);
                finish();
            }
        });
        viewModel.getPlanAddedEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                setResult(ADD_RESULT_OK);
                finish();
            }
        });
    }

    public static AddEditPlanViewModel makeViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        AddEditPlanViewModel viewModel = ViewModelProviders.of(activity, factory).get(AddEditPlanViewModel.class);
        return viewModel;
    }

    @Override
    public void onQuestionChange(Question question) {
        viewModel.setQuestion(question);
    }

    @Override
    public void onMultiChoiceChange(MultiChoice multiChoice) {
        viewModel.setMultiChoice(multiChoice);
    }
}
