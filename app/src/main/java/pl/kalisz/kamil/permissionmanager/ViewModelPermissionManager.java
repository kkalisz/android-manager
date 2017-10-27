package pl.kalisz.kamil.permissionmanager;

import android.support.annotation.NonNull;

import java.util.List;

import pl.kalisz.kamil.dialogmanager.DialogDefinition;
import pl.kalisz.kamil.sync.viewmodel.LiveDataManagerDelegate;

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
public class ViewModelPermissionManager extends LiveDataManagerDelegate<ViewModelPermissionManager.PermissionInfo,String,PermissionResult> implements PermissionHandler, PermissionManager{

    public class PermissionInfo
    {
        public PermissionInfo(@NonNull String requestCode, @NonNull List<String> permissions) {
            this.requestCode = requestCode;
            this.permissions = permissions;
        }

        private String requestCode;
        private List<String> permissions;
        @NonNull
        public String getRequestCode() {
            return requestCode;
        }

        @NonNull
        public List<String> getPermissions() {
            return permissions;
        }
    }
    @Override
    public void onPermissionResult(String requestCode, PermissionResult permissionResult) {
        resultStateHandler.returnResult(requestCode,permissionResult);
    }

    @Override
    public void registerPermissionHandler(@NonNull String key, @NonNull PermissionHandler permissionHandler) {
        getResultStateHandler().registerCallback(key,new PermissionResultCallback(permissionHandler));
    }

    @Override
    public int generateRequestCode(@NonNull String key) {
        return 0;
    }

    @Override
    public void requestPermissions(@NonNull String key, @NonNull List<String> permissions) {
        executeAction(key,new PermissionInfo(key,permissions));
    }
}
