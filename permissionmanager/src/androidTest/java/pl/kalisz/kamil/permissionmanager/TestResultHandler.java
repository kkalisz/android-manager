package pl.kalisz.kamil.permissionmanager;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;

import pl.kalisz.kamil.resultstatehandler.ResultStateHandler;
import pl.kalisz.kamil.statesaver.StateSaver;

public class TestResultHandler extends ResultStateHandler<String,PermissionResult,PermissionResultCallback> {
    public TestResultHandler(@NonNull Lifecycle lifecycle, @NonNull StateSaver stateSaver) {
        super(lifecycle, stateSaver);
    }
}
