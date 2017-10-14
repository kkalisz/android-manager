package pl.kalisz.kamil.dialogmanager;

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
public class DialogResult<DIALOG_RESULT> implements Parcelable
{
    private String requestCode;

    private DIALOG_RESULT dialogResult;

    public DialogResult(String requestCode, DIALOG_RESULT dialogResult) {
        this.requestCode = requestCode;
        this.dialogResult = dialogResult;
    }

    protected DialogResult(Parcel in) {
        requestCode = in.readString();
        dialogResult = (DIALOG_RESULT) in.readValue(DialogResult.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(requestCode);
        dest.writeValue(dialogResult);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DialogResult> CREATOR = new Creator<DialogResult>() {
        @Override
        public DialogResult createFromParcel(Parcel in) {
            return new DialogResult(in);
        }

        @Override
        public DialogResult[] newArray(int size) {
            return new DialogResult[size];
        }
    };

    public String getRequestCode() {
        return requestCode;
    }

    public DIALOG_RESULT getDialogResult() {
        return dialogResult;
    }
}
