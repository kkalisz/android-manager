package pl.kalisz.kamil.windowmanager;

import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pl.kalisz.kamil.resultstatehandler.ResultStateHandler;
import pl.kalisz.kamil.resultstatehandler.ResultStateHandlerLifecycleImpl;
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
public class WindowManagerImpl implements WindowManager
{
    private ResultStateHandler<Integer,IntentResult,IntentResultCallback> resultHandler;
    private ActivityStarter activityStarter;
    private RequestCodeGenerator requestCodeGenerator;

    public WindowManagerImpl(Lifecycle lifecycle, StateSaver stateSaver, ActivityStarter activityStarter) {
        this.activityStarter = activityStarter;
        StateSaver internalStateSaver = new StateSaver();
        stateSaver.registerStateHolder("WindowManagerImpl", internalStateSaver);
        resultHandler = new ResultStateHandlerLifecycleImpl<>(lifecycle, internalStateSaver);
        requestCodeGenerator = new RequestCodeGenerator(internalStateSaver);
    }

    @Override
    public void registerIntentHandler(@NonNull String key, @NonNull IntentHandler intentHandler) {
        resultHandler.registerCallback(generateRequestCode(key),new IntentResultCallback(intentHandler));
    }

    @Override
    public int generateRequestCode(@NonNull String key) {
        return requestCodeGenerator.getRequestCode(key);
    }

    @Override
    public void startActivity(@NonNull String key, @NonNull Intent intent, @Nullable Bundle options) {
        activityStarter.startActivityForResult(generateRequestCode(key),intent,options);
    }

    @Override
    public void startActivity(@NonNull String key, @NonNull Intent intent) {
        this.startActivity(key,intent,null);
    }

    public void returnResult(int requestCode, int resultCode,@NonNull Intent resultData)
    {
        String tagFromRequestCode = requestCodeGenerator.getTagFromRequestCode(requestCode);
        resultHandler.returnResult(requestCode,new IntentResult(tagFromRequestCode,resultCode,resultData));
    }
}
