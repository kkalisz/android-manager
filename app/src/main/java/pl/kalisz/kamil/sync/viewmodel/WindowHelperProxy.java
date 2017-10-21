package pl.kalisz.kamil.sync.viewmodel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import pl.kalisz.kamil.dialogmanager.DialogHandler;
import pl.kalisz.kamil.dialogmanager.ViewModelDialogManager;
import pl.kalisz.kamil.permissionmanager.PermissionHandler;
import pl.kalisz.kamil.permissionmanager.PermissionResult;
import pl.kalisz.kamil.permissionmanager.ViewModelPermissionManager;
import pl.kalisz.kamil.sync.WindowHelper;
import pl.kalisz.kamil.windowmanager.IntentHandler;
import pl.kalisz.kamil.windowmanager.IntentInfo;
import pl.kalisz.kamil.windowmanager.ViewModelWindowManager;

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
public class WindowHelperProxy
{
    public static void mediate(LifecycleOwner lifecycleOwner, final WindowHelper windowHelper, final ViewModelWindowHelper viewModelWindowHelper)
    {
        mediateDialogManager(lifecycleOwner, windowHelper, viewModelWindowHelper);
        mediateWindowManager(lifecycleOwner,windowHelper,viewModelWindowHelper);
        mediatePermissionManager(lifecycleOwner,windowHelper,viewModelWindowHelper);

    }

    private static void mediateDialogManager(LifecycleOwner lifecycleOwner, final WindowHelper windowHelper, ViewModelWindowHelper viewModelWindowHelper) {
        final ViewModelDialogManager dialogManager = viewModelWindowHelper.getDialogManager();
        final DialogHandler dialogHandler = new DialogHandler() {
            @Override
            public void onDialogResult(String requestCode, Object result) {
                dialogManager.onDialogResult(requestCode, result);
            }
        };

        for(String tagToListen : dialogManager.tagsToListen)
        {
            windowHelper.getDialogManager().registerDialogHandler(tagToListen, dialogHandler);
        }
        dialogManager.executeDelegate.observe(lifecycleOwner, new Observer<ViewModelDialogManager.DialogInfo>() {
            @Override
            public void onChanged(@NonNull ViewModelDialogManager.DialogInfo dialogInfo) {
                windowHelper.getDialogManager().registerDialogHandler(dialogInfo.getRequestCode(), dialogHandler);
                windowHelper.getDialogManager().showDialog(dialogInfo.getRequestCode(), dialogInfo.getDialogDefinition());
            }
        });
    }

    private static void mediateWindowManager(LifecycleOwner lifecycleOwner, final WindowHelper windowHelper, ViewModelWindowHelper viewModelWindowHelper) {
        final ViewModelWindowManager windowManager = viewModelWindowHelper.getWindowManager();
        final IntentHandler intentHandler = new IntentHandler() {
            @Override
            public void onWindowResult(String requestCode, int resultCode, Intent resultData) {
                windowManager.onWindowResult(requestCode,resultCode,resultData);
            }
        };

        for(String tagToListen : windowManager.tagsToListen)
        {
            windowHelper.getWindowManager().registerIntentHandler(tagToListen, intentHandler);
        }
        windowManager.executeDelegate.observe(lifecycleOwner, new Observer<IntentInfo>() {
            @Override
            public void onChanged(@NonNull IntentInfo intentInfo) {
                windowHelper.getWindowManager().registerIntentHandler(intentInfo.getRequestCode(), intentHandler);
                windowHelper.getWindowManager().startActivity(intentInfo.getRequestCode(), intentInfo.getIntent(),intentInfo.getOptions());

            }
        });
    }

    private static void mediatePermissionManager(LifecycleOwner lifecycleOwner, final WindowHelper windowHelper, ViewModelWindowHelper viewModelWindowHelper) {
        final ViewModelPermissionManager dialogManager = viewModelWindowHelper.getPermissionManager();
        final PermissionHandler intentHandler = new PermissionHandler() {
            @Override
            public void onPermissionResult(String requestCode, PermissionResult permissionResult) {
                dialogManager.onPermissionResult(requestCode,permissionResult);
            }
        };

        for(String tagToListen : dialogManager.tagsToListen)
        {
            windowHelper.getPermissionManager().registerPermissionHandler(tagToListen, intentHandler);
        }
        dialogManager.executeDelegate.observe(lifecycleOwner, new Observer<ViewModelPermissionManager.PermissionInfo>() {
            @Override
            public void onChanged(@Nullable ViewModelPermissionManager.PermissionInfo permissionInfo) {
                windowHelper.getPermissionManager().requestPermissions(permissionInfo.getRequestCode(),permissionInfo.getPermissions());
                windowHelper.getPermissionManager().registerPermissionHandler(permissionInfo.getRequestCode(),intentHandler);
            }
        });
    }
}
