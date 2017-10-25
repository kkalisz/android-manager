package pl.kalisz.kamil.resultstatehandler;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

import pl.kalisz.kamil.statesaver.StateSaver;

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

/**
 * @param <KEY_TYPE>      type of key used to matching result with callback
 * @param <RESULT_TYPE>   type of result should be Parcelable or Serializable
 * @param <CALLBACK_TYPE> type of callback method
 */
public class ResultStateHandlerLifecycleImpl<KEY_TYPE, RESULT_TYPE, CALLBACK_TYPE extends ResultStateCallback<RESULT_TYPE>> extends ResultStateHandlerStateLessImpl<KEY_TYPE, RESULT_TYPE, CALLBACK_TYPE> implements LifecycleObserver {
    private Lifecycle lifecycle;


    /**
     * resumes handler, in resume state handler can propagate results
     */
    @OnLifecycleEvent(value = Lifecycle.Event.ON_RESUME)
    void onResume() {
        tryPropagatePendingResults();
    }

    @Override
    protected boolean canPropagateResults() {
       return lifecycle!= null && lifecycle.getCurrentState().isAtLeast(Lifecycle.State.RESUMED);
    }

    public ResultStateHandlerLifecycleImpl(@NonNull Lifecycle lifecycle, @NonNull StateSaver stateSaver, String saveStateTag) {
        super(stateSaver, saveStateTag);
        this.lifecycle = lifecycle;
        lifecycle.addObserver(this);
        tryPropagatePendingResults();
    }

    public ResultStateHandlerLifecycleImpl(@NonNull Lifecycle lifecycle, @NonNull StateSaver stateSaver) {
        this(lifecycle, stateSaver, SAVE_STATE_TAG);
    }
}
