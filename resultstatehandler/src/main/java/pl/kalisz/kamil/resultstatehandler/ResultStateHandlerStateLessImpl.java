package pl.kalisz.kamil.resultstatehandler;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.kalisz.kamil.statesaver.StateHolder;
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

/**
 *
 * @param <KEY_TYPE> type of key used to matching result with callback
 * @param <RESULT_TYPE> type of result should be Parcelable or Serializable
 * @param <CALLBACK_TYPE> type of callback method
 */
public class ResultStateHandlerStateLessImpl<KEY_TYPE,RESULT_TYPE,CALLBACK_TYPE extends ResultStateCallback<RESULT_TYPE>>
        implements LifecycleObserver, StateHolder<PendingResultsState<KEY_TYPE,RESULT_TYPE>>,ResultStateHandler<KEY_TYPE,RESULT_TYPE,CALLBACK_TYPE> {
    protected static final String SAVE_STATE_TAG = "RESULT_HANDLER_STATE";
    private Map<KEY_TYPE,CALLBACK_TYPE> callbacks = new HashMap<>();
    private Map<KEY_TYPE,RESULT_TYPE> pendingResults = new HashMap<>();

    public ResultStateHandlerStateLessImpl( @NonNull StateSaver stateSaver) {
        this(stateSaver, SAVE_STATE_TAG);
    }

    public ResultStateHandlerStateLessImpl( @NonNull StateSaver stateSaver, String saveStateTag) {
        stateSaver.registerStateHolder(saveStateTag,this);
    }

    /**
     * register callback. If there is result for this callback
     *  and state is resumed, result is returned to callback method
     * @param key unique key for callback should be matched with result key
     * @param callback callback method
     */
    public void registerCallback(@NonNull KEY_TYPE key, @NonNull CALLBACK_TYPE callback)
    {
        callbacks.put(key,callback);
        if(canPropagateResults() && pendingResults.containsKey(key))
        {
            returnResultInternal(key);
        }
    }

    /**
     * @return true if current {@link Lifecycle#getCurrentState()} state is at least {@link Lifecycle.Event#ON_RESUME}
     */
    protected boolean canPropagateResults() {
        return true;
    }

    /**
     *  register value for return, if callback for key exists
     *  and state is resumed, result is returned to callback method
     * @param key unique key for result should be matched with callback key
     * @param result result to propagate
     */
    public void returnResult(@NonNull KEY_TYPE key, @Nullable RESULT_TYPE result)
    {
        pendingResults.put(key,result);
        if(canPropagateResults() && callbacks.containsKey(key))
        {
            returnResultInternal(key);
        }
    }

    /**
     * @param key for result to be returned
     */
    private void returnResultInternal(@Nullable KEY_TYPE key) {
        RESULT_TYPE result2 = pendingResults.remove(key);
        callbacks.get(key).callBack(result2);
    }

    /**
     *  Method iterates through all pending results,
     *  if there are callbacks for results they are propagated.
     */
    protected void tryPropagatePendingResults() {
        List<KEY_TYPE> savedResults = new ArrayList<>(pendingResults.keySet());
        for(KEY_TYPE key : savedResults)
        {
            if(callbacks.containsKey(key))
            {
                returnResultInternal(key);
            }
        }
    }

    @Override
    public PendingResultsState<KEY_TYPE, RESULT_TYPE> onSaveState() {
        return new PendingResultsState<KEY_TYPE,RESULT_TYPE>(pendingResults);
    }

    @Override
    public void onRestoreState(@Nullable PendingResultsState<KEY_TYPE, RESULT_TYPE> restoredState) {
        if(restoredState!=null)
        {
            //TODO try propagate only restored results
            pendingResults.putAll(restoredState.pendingResults);
            if(canPropagateResults()) {
                tryPropagatePendingResults();
            }
        }
    }
}
