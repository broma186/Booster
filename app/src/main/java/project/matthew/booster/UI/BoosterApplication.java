package project.matthew.booster.UI;

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Matthew on 30/04/2018.
 */

public class BoosterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
