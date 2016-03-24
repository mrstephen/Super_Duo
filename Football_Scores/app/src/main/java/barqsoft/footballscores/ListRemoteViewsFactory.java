package barqsoft.footballscores;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Stephen on 3/22/2016.
 */
public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private Cursor mCursor;
    private int mAppWidgetId;

    public ListRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        // Refresh the cursor
        if (mCursor != null) {
            mCursor.close();
        }

        String[] dates = new String[PagerFragment.NUM_PAGES];

        for(int idx = 0; idx < PagerFragment.NUM_PAGES; idx++) {
            Date fragmentdate = new Date(System.currentTimeMillis()+((idx-PagerFragment.NUM_DAYS_PAST)*86400000));
            SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
            dates[idx] = mformat.format(fragmentdate);
        }

        mCursor = mContext.getContentResolver().query(DatabaseContract.scores_table.buildScoreWithDate(), null, null,
                dates, null);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mCursor != null)
            return mCursor.getCount();
        else
            return 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        // position will always range from 0 to getCount() - 1.

        // Construct a RemoteViews item based on the app widget item XML file, and set the
        // text based on the position.
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.list_view_widget_item);
        rv.setTextViewText(R.id.testTextView, "Match "+i);

//        // Next, set a fill-intent, which will be used to fill in the pending intent template
//        // that is set on the collection view in StackWidgetProvider.
//        Bundle extras = new Bundle();
//        extras.putInt(StackWidgetProvider.EXTRA_ITEM, position);
//        Intent fillInIntent = new Intent();
//        fillInIntent.putExtras(extras);
//        // Make it possible to distinguish the individual on-click
//        // action of a given item
//        rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent);

        // Return the RemoteViews object.
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
