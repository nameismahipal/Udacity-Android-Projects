package www.androidcitizen.com.bakeit.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;


/**
 * Created by Mahi on 11/09/18.
 * www.androidcitizen.com
 */

public class ListViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewFactory(this.getApplicationContext());
    }
}
