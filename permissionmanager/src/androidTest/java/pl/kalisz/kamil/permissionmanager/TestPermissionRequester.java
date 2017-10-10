package pl.kalisz.kamil.permissionmanager;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import java.util.Arrays;

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
public class TestPermissionRequester implements PermissionRequester
{
    private PermissionManagerImpl permissionManager;

    public void setPermissionManager(PermissionManagerImpl permissionManager) {
        this.permissionManager = permissionManager;
    }

    @Override
    public void requestPermissions(@NonNull int requestCode, String[] permissions) {
        int [] grantResults = new int[permissions.length];
        Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
        permissionManager.returnResult(requestCode,permissions, grantResults);
    }
}
