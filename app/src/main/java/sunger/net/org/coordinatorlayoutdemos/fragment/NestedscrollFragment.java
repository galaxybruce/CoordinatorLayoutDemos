package sunger.net.org.coordinatorlayoutdemos.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sunger.net.org.coordinatorlayoutdemos.R;
import sunger.net.org.coordinatorlayoutdemos.adapter.RecyclerAdapter;

/**
 * Created by sunger on 2015/12/15.
 */
public class NestedscrollFragment extends Fragment {

    private ArrayList<String> stringArrayList;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    private NestedScrollView mNestedScrollView;

    private ScrollFeedbackRecyclerView.Callbacks mCallbacks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nestedscroll, container, false);

        if(getContext() instanceof ScrollFeedbackRecyclerView.Callbacks)
        {
            mCallbacks = (ScrollFeedbackRecyclerView.Callbacks)getContext();
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNestedScrollView = (NestedScrollView)view.findViewById(R.id.nest_scrollview);
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == 0 && oldScrollY > scrollY) {
                    Log.e("aaaaaaaaaaaaaaaaaaaaa", "index 0 visible");
                    if(mCallbacks.isAppBarCollapsed()) {
                        mCallbacks.setExpanded(true);
                    }
                }
            }
        });
    }
}
