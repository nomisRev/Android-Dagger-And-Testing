package be.vergauwen.simon.androidtestingexample;

import android.app.Application;
import be.vergauwen.simon.androidtestingexample.core.di.ApplicationComponent;
import be.vergauwen.simon.androidtestingexample.core.di.ApplicationModule;
import be.vergauwen.simon.androidtestingexample.core.di.DaggerApplicationComponent;
import be.vergauwen.simon.androidtestingexample.core.di.ServiceModule;



public class ExampleAppImpl extends Application implements ExampleApp {
    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
            .serviceModule(new ServiceModule())
            .applicationModule(new ApplicationModule(this))
            .build();
    }

    @Override
    public ApplicationComponent getComponent() {
        return component;
    }
}
