package pl.kalisz.kamil.resultstatehandler;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

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

/**
 * representation of handler state saved between window rebuilds/saves
 * @param <KEY_TYPE> type of key used to matching result with callback
 * @param <RESULT_TYPE> type of result should be Parcelable or Serializable
 */
public class PendingResultsState<KEY_TYPE,RESULT_TYPE> implements Parcelable {
    Map<KEY_TYPE,RESULT_TYPE> pendingResults = new HashMap<>();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pendingResults.size());
        for (Map.Entry<KEY_TYPE, RESULT_TYPE> entry : this.pendingResults.entrySet()) {
            dest.writeValue(entry.getKey());
            dest.writeValue(entry.getValue());
        }
    }

    public PendingResultsState(@NonNull Map<KEY_TYPE,RESULT_TYPE> pendingResults)
    {
        this.pendingResults.putAll(pendingResults);
    }

    protected PendingResultsState(Parcel in) {
        int pendingResultsSize = in.readInt();
        this.pendingResults = new HashMap<KEY_TYPE, RESULT_TYPE>(pendingResultsSize);
        for (int i = 0; i < pendingResultsSize; i++) {
            KEY_TYPE key = (KEY_TYPE) in.readValue(getClass().getClassLoader());
            RESULT_TYPE value = (RESULT_TYPE) in.readValue(getClass().getClassLoader());
            this.pendingResults.put(key, value);
        }
    }

    public static final Parcelable.Creator<PendingResultsState> CREATOR = new Parcelable.Creator<PendingResultsState>() {
        @Override
        public PendingResultsState createFromParcel(Parcel source) {
            return new PendingResultsState(source);
        }

        @Override
        public PendingResultsState[] newArray(int size) {
            return new PendingResultsState[size];
        }
    };
}
