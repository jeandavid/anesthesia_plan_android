package com.elishou.anesthesiaplan.multichoicelist;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.elishou.anesthesiaplan.data.Choice;

import java.util.List;

public class ChoicesBindings {

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:items")
    public static void setItems(RecyclerView recyclerView, List<Choice> items) {
        ChoicesAdapter adapter = (ChoicesAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(items);
        }
    }
}
