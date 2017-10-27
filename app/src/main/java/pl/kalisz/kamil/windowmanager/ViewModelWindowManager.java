package pl.kalisz.kamil.windowmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pl.kalisz.kamil.sync.viewmodel.LiveDataManagerDelegate;

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
public class ViewModelWindowManager extends LiveDataManagerDelegate<IntentInfo,String,IntentResult> implements IntentHandler, WindowManager
{
    @Override
    public void onWindowResult(String requestCode, int resultCode, Intent resultData) {
        resultStateHandler.returnResult(requestCode,new IntentResult(requestCode,resultCode,resultData));
    }

    @Override
    public void registerIntentHandler(@NonNull String key, @NonNull IntentHandler intentHandler) {
        getResultStateHandler().registerCallback(key ,new IntentResultCallback(intentHandler));
    }

    @Override
    public int generateRequestCode(@NonNull String key) {
        return 0;
    }

    @Override
    public void startActivity(@NonNull String key, @NonNull Intent intent, @Nullable Bundle options)
    {
        executeAction(key,new IntentInfo(key,intent,options));
    }

    @Override
    public void startActivity(@NonNull String key, @NonNull Intent intent) {
        this.startActivity(key,intent,null);
    }
}
