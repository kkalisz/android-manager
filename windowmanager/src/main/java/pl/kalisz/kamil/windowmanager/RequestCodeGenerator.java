package pl.kalisz.kamil.windowmanager;

import android.os.Bundle;
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
public class RequestCodeGenerator {

    private static final String KEYS = "keys";
    private static final String VALUES = "values";
    private Map<String,Integer> generatedRequestCodes = new HashMap<>();

    private StateHolder<Bundle> stateHolder = new StateHolder<Bundle>() {
        @Nullable
        @Override
        public Bundle onSaveState()
        {
            Bundle savedSate = new Bundle();
            savedSate.putStringArrayList(KEYS, new ArrayList<>(generatedRequestCodes.keySet()));
            savedSate.putIntegerArrayList(VALUES, new ArrayList<>(generatedRequestCodes.values()));
            return savedSate;
        }

        @Override
        public void onRestoreState(@Nullable Bundle restoredState)
        {
            List<String> keys = restoredState.getStringArrayList(KEYS);
            List<Integer> values = restoredState.getIntegerArrayList(VALUES);
            for(int i =0; i< keys.size(); i++)
            {
                generatedRequestCodes.put(keys.get(i),values.get(i));
            }
        }
    };

    public RequestCodeGenerator(@NonNull StateSaver stateSaver)
    {
        stateSaver.registerStateHolder("REQUEST_CODE_GENERATOR",stateHolder);
    }

    @NonNull
    public int getRequestCode(@NonNull String requestTag)
    {
        if(!generatedRequestCodes.containsKey(requestTag))
        {
            generatedRequestCodes.put(requestTag,generatedRequestCodes.size()+1);
        }
        return generatedRequestCodes.get(requestTag);
    }

    @Nullable
    public String getTagFromRequestCode(int requestCode)
    {
        for(Map.Entry<String, Integer> entry :generatedRequestCodes.entrySet())
        {
            if(entry.getValue() == requestCode)
            {
                return entry.getKey();
            }
        }
        return null;
    }
}
