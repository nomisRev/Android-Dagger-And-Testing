package be.vergauwen.simon.androidtestingexample;

import android.app.Application;
import be.vergauwen.simon.androidtestingexample.core.di.ApplicationComponent;
import be.vergauwen.simon.androidtestingexample.core.di.DaggerApplicationComponent;
import be.vergauwen.simon.androidtestingexample.core.di.TestApplicationModule;
import be.vergauwen.simon.androidtestingexample.core.di.TestServiceModule;

public class TestExampleAppImpl extends Application implements ExampleApp {
    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
            .applicationModule(new TestApplicationModule(this))
            .serviceModule(new TestServiceModule())
            .build();
    }

    @Override
    public ApplicationComponent getComponent() {
        return component;
    }
}