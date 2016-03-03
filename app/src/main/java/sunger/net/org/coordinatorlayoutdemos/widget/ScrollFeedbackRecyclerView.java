package sunger.net.org.coordinatorlayoutdemos.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/3/3.
 * http://stackoverflow.com/questions/34524625/how-to-expand-appbarlayout-when-scrolling-down-reaches-at-top-of-the-recyclervie
 */
public class ScrollFeedbackRecyclerView extends RecyclerView {

    private WeakReference<Callbacks> mCallbacks;

    public ScrollFeedbackRecyclerView(Context context) {
        super(context);
        attachCallbacks(context);
    }

    public ScrollFeedbackRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        attachCallbacks(context);
    }

    /*If the first completely visible item in the RecyclerView is at
    index 0, then we're at the top of the list, so we want the AppBar to expand
    **if the AppBar is also collapsed** (otherwise the AppBar will constantly
    attempt to expand).
    */
    @Override
    public void onScrolled(int dx, int dy) {
        if(((LinearLayoutManager)getLayoutManager()).findFirstCompletelyVisibleItemPosition() == 0) {
            Log.e(getClass().getSimpleName(), "index 0 visible");
            if(mCallbacks.get().isAppBarCollapsed()) {
                mCallbacks.get().setExpanded(true);
            }
        }
        super.onScrolled(dx, dy);
    }

    /* the findFirstCompletelyVisibleItem() method is only available with
    LinearLayoutManager and its subclasses, so test for it when setting the
    LayoutManager
    */
    @Override
    public void setLayoutManager(LayoutManager layout) {
        if(!(layout instanceof LinearLayoutManager)) {
            throw new IllegalArgumentException(layout.toString() + " must be of type LinearLayoutManager");
        }
        super.setLayoutManager(layout);
    }

    private void attachCallbacks(Context context) {

        try {
            mCallbacks = new WeakReference<>((Callbacks)context);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " +
                    "ScrollFeedbackRecyclerView.Callbacks");
        }

    }

    /* Necessary to interact with the AppBarLayout in the hosting Activity
    */
    public interface Callbacks {

        boolean isAppBarCollapsed();
        void setExpanded(boolean expanded);

    }
}
