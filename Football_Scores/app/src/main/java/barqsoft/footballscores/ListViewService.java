package barqsoft.footballscores;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Stephen on 3/22/2016.
 */
public class ListViewService extends RemoteViewsService{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
