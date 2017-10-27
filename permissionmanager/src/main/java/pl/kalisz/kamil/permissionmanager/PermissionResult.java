package pl.kalisz.kamil.permissionmanager;

import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
public class PermissionResult implements Parcelable
{
    private String requestCode;

    private HashMap<String,Integer> permissionResult = new HashMap<>();

    public PermissionResult(String tagFromRequestCode, String[] permissions, int[] grantResults) {
        this.requestCode = tagFromRequestCode;
        for(int i = 0; i < permissions.length ; i++)
        {
            permissionResult.put(permissions[i],grantResults[i]);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.requestCode);
        dest.writeStringArray(this.permissionResult.keySet().toArray(new String[permissionResult.size()]));

        Collection<Integer> rawValues = this.permissionResult.values();
        int[] values = new int[permissionResult.size()];
        Iterator<Integer> iterator = rawValues.iterator();
        int i =0;
        while (iterator.hasNext())
        {
            values[i] = iterator.next();
            i++;
        }
        dest.writeIntArray(values);
    }

    protected PermissionResult(Parcel in) {
        this.requestCode = in.readString();
        String [] keys =  in.createStringArray();
        int [] values = in.createIntArray();
        for(int i=0;i< keys.length;i++)
        {
            permissionResult.put(keys[i],values[i]);
        }

    }

    public static final Creator<PermissionResult> CREATOR = new Creator<PermissionResult>() {
        @Override
        public PermissionResult createFromParcel(Parcel source) {
            return new PermissionResult(source);
        }

        @Override
        public PermissionResult[] newArray(int size) {
            return new PermissionResult[size];
        }
    };

    public String getRequestCode() {
        return requestCode;
    }

    public List<String> getPermissions() {
        return new ArrayList(permissionResult.keySet());
    }

    public List<Integer> getGrantResults() {
        return new ArrayList<>(permissionResult.values());
    }

    public boolean wasAllPermissionsGranted()
    {
        for(int grantResult : permissionResult.values())
        {
            if(grantResult == PackageManager.PERMISSION_DENIED)
            {
                return false;
            }
        }
        return true;
    }

    public boolean wasPermissionsGranted(List<String> permissions)
    {
        for(String permission : permissions)
        {
            if(PackageManager.PERMISSION_DENIED == permissionResult.get(permission))
            {
                return false;
            }
        }
        return true;
    }
}
