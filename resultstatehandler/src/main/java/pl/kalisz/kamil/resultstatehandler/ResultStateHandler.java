package pl.kalisz.kamil.resultstatehandler;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 *
 * @param <KEY_TYPE> type of key used to matching result with callback
 * @param <RESULT_TYPE> type of result should be Parcelable or Serializable
 * @param <CALLBACK_TYPE> type of callback method
 */
public class ResultStateHandler<KEY_TYPE,RESULT_TYPE,CALLBACK_TYPE extends ResultStateCallback<RESULT_TYPE>>
{
    private Map<KEY_TYPE,CALLBACK_TYPE> callbacks = new HashMap<>();
    private Map<KEY_TYPE,RESULT_TYPE> pendingResults = new HashMap<>();
    private boolean resumed;

    /**
     * register callback. If there is result for this callback
     *  and state is resumed, result is returned to callback method
     * @param key unique key for callback should be matched with result key
     * @param callback callback method
     */
    public void registerCallback(@NonNull KEY_TYPE key, @NonNull CALLBACK_TYPE callback)
    {
        callbacks.put(key,callback);
        if(resumed && pendingResults.containsKey(key))
        {
            returnResultInternal(key);
        }
    }

    /**
     *  register value for return, if callback for key exists
     *  and state is resumed, result is returned to callback method
     * @param key unique key for result should be matched with callback key
     * @param result result to propagate
     */
    public void returnResult(KEY_TYPE key, RESULT_TYPE result)
    {
        pendingResults.put(key,result);
        if(resumed && callbacks.containsKey(key))
        {
            returnResultInternal(key);
        }
    }

    private void returnResultInternal(KEY_TYPE key) {
        RESULT_TYPE result2 = pendingResults.remove(key);
        callbacks.get(key).callBack(result2);
    }

    /**
     * resumes handler, in resume state handler can propagate results
     */
    public void onResume()
    {
        resumed = true;
        tryPropagatePendingResults();
    }

    private void tryPropagatePendingResults() {
        List<KEY_TYPE> savedResults = new ArrayList<>(pendingResults.keySet());
        for(KEY_TYPE key : savedResults)
        {
            if(callbacks.containsKey(key))
            {
                returnResultInternal(key);
            }
        }
    }

    /**
     * pauses handler, in pause state handler can't propagate results
     */
    public void onPause()
    {
        resumed = false;
    }

    public PendingResultsState saveState()
    {
        return new PendingResultsState<KEY_TYPE,RESULT_TYPE>(pendingResults);
    }

    public void restoreState(PendingResultsState<KEY_TYPE,RESULT_TYPE> restoredResults)
    {
        if(restoredResults!=null)
        {
            //TODO try propagate only restored results
            pendingResults.putAll(restoredResults.pendingResults);
            if(resumed) {
                tryPropagatePendingResults();
            }
        }
    }
}
