package pl.kalisz.kamil.sync;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import pl.kalisz.kamil.dialogmanager.DialogDefinition;
import pl.kalisz.kamil.dialogmanager.DialogHandler;
import pl.kalisz.kamil.dialogmanager.DialogRequester;
import pl.kalisz.kamil.dialogmanager.RegistryDialogRequester;
import pl.kalisz.kamil.permissionmanager.PermissionRequester;
import pl.kalisz.kamil.statesaver.StateSaver;
import pl.kalisz.kamil.sync.WindowHelper;
import pl.kalisz.kamil.sync.WindowHelperImpl;
import pl.kalisz.kamil.windowmanager.ActivityStarter;

/**
 * Copyright (C) 2016 Kamil Kalisz.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class BaseFragment extends Fragment implements PermissionRequester, ActivityStarter, DialogHandler<Object> {
    protected WindowHelperImpl windowHelper;
    protected StateSaver stateSaver = new StateSaver();
    protected RegistryDialogRequester registryDialogRequester;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stateSaver.onRestoreState(savedInstanceState);
        registryDialogRequester = new RegistryDialogRequester(getContext(),getFragmentManager());
        windowHelper = new WindowHelperImpl(getLifecycle(),stateSaver,registryDialogRequester,this,this);
    }

    @Override
    public void requestPermissions(@NonNull int requestCode, String[] permissions) {
        this.requestPermissions(permissions,requestCode);
    }

    @Override
    public void startActivityForResult(@NonNull int requestCode, @NonNull Intent intent, @Nullable Bundle options) {
       super.startActivityForResult(intent,requestCode,options);
    }

    @Override
    public void onDialogResult(String requestCode, Object result) {
        windowHelper.getDialogManager().returnResult(requestCode,result);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        windowHelper.getWindowManager().returnResult(requestCode,resultCode,data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        windowHelper.getPermissionManager().returnResult(requestCode,permissions,grantResults);
    }

    public WindowHelper getWindowHelper() {
        return windowHelper;
    }
}
