package pl.kalisz.kamil.statesaver;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
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
public class StateSaver implements StateHolder<Bundle>
{
    @NonNull
    private Map<String,StateHolder> registeredStateHolders = new HashMap<>();
    @NonNull
    private Map<String,Object> restoredStates = new HashMap<>();

    public void registerStateHolder(String tag, StateHolder stateHolder)
    {
        registeredStateHolders.put(tag,stateHolder);
        tryRestoreState(tag, stateHolder);
    }

    private void tryRestoreState(String tag, StateHolder stateHolder) {
        if(restoredStates.containsKey(tag))
        {
            Object state = restoredStates.remove(tag);
            stateHolder.onRestoreState(state);
        }
    }

    @Nullable
    @Override
    public Bundle onSaveState() {
        Bundle bundle = new Bundle();
        for(Map.Entry<String, StateHolder> entry : registeredStateHolders.entrySet())
        {
            Object savedState = entry.getValue().onSaveState();
            if(savedState instanceof Parcelable)
            {
                bundle.putParcelable(entry.getKey(), (Parcelable) entry.getValue().onSaveState());
            }
            else if( savedState instanceof Serializable)
            {
                bundle.putSerializable(entry.getKey(), (Serializable) entry.getValue().onSaveState());
            }
        }
        return bundle;
    }

    @Override
    public void onRestoreState(@Nullable Bundle restoredState)
    {
        if(restoredState != null)
        {
            restoreStates(restoredState);
        }
    }

    private void restoreStates(@Nullable Bundle restoredState) {
        for(String key : restoredState.keySet())
        {
            StateHolder stateHolder = registeredStateHolders.get(key);
            Object state = restoredState.get(key);
            if(stateHolder != null)
            {
                stateHolder.onRestoreState(state);
            }
            else
            {
                restoredStates.put(key,state);
            }
        }
    }
}
