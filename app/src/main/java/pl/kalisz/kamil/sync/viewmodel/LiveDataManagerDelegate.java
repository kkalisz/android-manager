package pl.kalisz.kamil.sync.viewmodel;

import android.os.Looper;

import java.util.HashSet;
import java.util.Set;

import pl.kalisz.kamil.resultstatehandler.ResultStateCallback;
import pl.kalisz.kamil.resultstatehandler.ResultStateHandler;
import pl.kalisz.kamil.resultstatehandler.ResultStateHandlerStateLessImpl;
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
public abstract class LiveDataManagerDelegate<DELEGATED_DATA,TAG_TYPE,RESULT_TYPE>
{
    protected SingleLiveEvent<DELEGATED_DATA> executeDelegate = new SingleLiveEvent<>();
    protected ResultStateHandler<TAG_TYPE,RESULT_TYPE, ResultStateCallback<RESULT_TYPE>> resultStateHandler = new ResultStateHandlerStateLessImpl<>(new StateSaver());
    protected Set<TAG_TYPE> tagsToListen = new HashSet<>();

    public void executeAction(TAG_TYPE tag, DELEGATED_DATA delegatedData)
    {
        tagsToListen.add(tag);
        if(isMainThread()) {
            executeDelegate.setValue(delegatedData);
        }
        else executeDelegate.postValue(delegatedData);
    }

    private boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public ResultStateHandler<TAG_TYPE, RESULT_TYPE, ResultStateCallback<RESULT_TYPE>> getResultStateHandler() {
        return resultStateHandler;
    }
}
