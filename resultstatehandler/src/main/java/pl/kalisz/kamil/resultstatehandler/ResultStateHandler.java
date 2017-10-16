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
public interface ResultStateHandler<KEY_TYPE,RESULT_TYPE,CALLBACK_TYPE extends ResultStateCallback<RESULT_TYPE>> {

    /**
     * register callback. If there is result for this callback
     *  and state is resumed, result is returned to callback method
     * @param key unique key for callback should be matched with result key
     * @param callback callback method
     */
    void registerCallback(@NonNull KEY_TYPE key, @NonNull CALLBACK_TYPE callback);

    /**
     *  register value for return, if callback for key exists
     *  and state is resumed, result is returned to callback method
     * @param key unique key for result should be matched with callback key
     * @param result result to propagate
     */
    void returnResult(@NonNull KEY_TYPE key, @Nullable RESULT_TYPE result);

}
