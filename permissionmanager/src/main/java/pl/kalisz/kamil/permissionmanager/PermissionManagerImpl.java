package pl.kalisz.kamil.permissionmanager;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;

import java.util.List;

import pl.kalisz.kamil.resultstatehandler.ResultStateHandler;
import pl.kalisz.kamil.resultstatehandler.ResultStateHandlerLifecycleImpl;
import pl.kalisz.kamil.statesaver.StateSaver;

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
public class PermissionManagerImpl implements PermissionManager
{
    private ResultStateHandler<Integer,PermissionResult,PermissionResultCallback> resultHandler;
    private PermissionRequester permissionRequester;
    private RequestCodeGenerator requestCodeGenerator;

    public PermissionManagerImpl(Lifecycle lifecycle, StateSaver stateSaver, PermissionRequester permissionRequester) {
        this.permissionRequester = permissionRequester;
        StateSaver internalStateSaver = new StateSaver();
        stateSaver.registerStateHolder("PermissionManagerImpl", internalStateSaver);
        resultHandler = new ResultStateHandlerLifecycleImpl<>(lifecycle, internalStateSaver);
        requestCodeGenerator = new RequestCodeGenerator(internalStateSaver);
    }

    @Override
    public void registerPermissionHandler(@NonNull String key, @NonNull PermissionHandler permissionHandler) {
        resultHandler.registerCallback(generateRequestCode(key),new PermissionResultCallback(permissionHandler));
    }

    @Override
    public int generateRequestCode(@NonNull String key) {
        return requestCodeGenerator.getRequestCode(key);
    }

    @Override
    public void requestPermissions(@NonNull String key, @NonNull List<String> permissions) {
        permissionRequester.requestPermissions(generateRequestCode(key),permissions.toArray(new String[permissions.size()]));
    }

    public void returnResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        String tagFromRequestCode = requestCodeGenerator.getTagFromRequestCode(requestCode);
        resultHandler.returnResult(requestCode,new PermissionResult(tagFromRequestCode,permissions,grantResults));
    }
}
