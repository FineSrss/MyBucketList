package bibucketlist.com.mybucketlist;

import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by pineone on 2017-06-02.
 */

public abstract class RealmBaseActivity extends AppCompatActivity {

    private RealmConfiguration realmConfiguration;

    protected RealmConfiguration getRealmConfig(){
        if(realmConfiguration == null){
            realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        }

        return realmConfiguration;
    }

    protected void resetRealm() { Realm.deleteRealm(getRealmConfig()); }
}
