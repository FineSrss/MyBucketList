package bibucketlist.com.mybucketlist;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by pineone on 2017-06-02.
 */

public class BucketApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
