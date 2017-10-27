package pl.kalisz.kamil.sync;

import android.arch.lifecycle.Lifecycle;

import pl.kalisz.kamil.dialogmanager.DialogManager;
import pl.kalisz.kamil.dialogmanager.DialogManagerImpl;
import pl.kalisz.kamil.dialogmanager.DialogRequester;
import pl.kalisz.kamil.permissionmanager.PermissionManager;
import pl.kalisz.kamil.permissionmanager.PermissionManagerImpl;
import pl.kalisz.kamil.permissionmanager.PermissionRequester;
import pl.kalisz.kamil.statesaver.StateSaver;
import pl.kalisz.kamil.windowmanager.ActivityStarter;
import pl.kalisz.kamil.windowmanager.WindowManager;
import pl.kalisz.kamil.windowmanager.WindowManagerImpl;

/**
 * Copyright (C) 2017 Kamil Kalisz.
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
public class WindowHelperImpl implements WindowHelper
{
    private DialogManagerImpl dialogManager;
    private  PermissionManagerImpl permissionManager;
    private WindowManagerImpl windowManager;
    private StateSaver stateSaverInternal = new StateSaver();

    public WindowHelperImpl(Lifecycle lifecycle, StateSaver stateSaver, DialogRequester dialogRequester, PermissionRequester permissionRequester, ActivityStarter activityStarter) {
        stateSaver.registerStateHolder("WindowHelperImpl",stateSaverInternal);
        this.dialogManager = new DialogManagerImpl(lifecycle, stateSaverInternal, dialogRequester);
        this.permissionManager = new PermissionManagerImpl(lifecycle,stateSaverInternal,permissionRequester);
        this.windowManager = new WindowManagerImpl(lifecycle,stateSaverInternal,activityStarter);
    }

    @Override
    public DialogManagerImpl getDialogManager() {
        return dialogManager;
    }

    @Override
    public PermissionManagerImpl getPermissionManager() {
        return permissionManager;
    }

    @Override
    public WindowManagerImpl getWindowManager() {
        return windowManager;
    }

    @Override
    public StateSaver getStateSaver() {
        return stateSaverInternal;
    }
}
