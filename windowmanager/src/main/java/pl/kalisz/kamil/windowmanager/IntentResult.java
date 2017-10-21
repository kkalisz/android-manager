package pl.kalisz.kamil.windowmanager;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;


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
public class IntentResult implements Parcelable
{
    public IntentResult(String requestCode, int resultCode, Intent resultData) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.resultData = resultData;
    }

    private String requestCode;
    private int resultCode;
    private Intent resultData;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(requestCode);
        dest.writeInt(this.resultCode);
        dest.writeParcelable(this.resultData, flags);
    }

    protected IntentResult(Parcel in) {
        this.requestCode = in.readString();
        this.resultCode = in.readInt();
        this.resultData = in.readParcelable(Intent.class.getClassLoader());
    }

    public static final Creator<IntentResult> CREATOR = new Creator<IntentResult>() {
        @Override
        public IntentResult createFromParcel(Parcel source) {
            return new IntentResult(source);
        }

        @Override
        public IntentResult[] newArray(int size) {
            return new IntentResult[size];
        }
    };

    public int getResultCode() {
        return resultCode;
    }

    public Intent getResultData() {
        return resultData;
    }

    public String getRequestCode() {
        return requestCode;
    }
}
