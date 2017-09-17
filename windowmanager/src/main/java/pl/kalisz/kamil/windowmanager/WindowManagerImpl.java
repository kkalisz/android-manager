package pl.kalisz.kamil.windowmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import pl.kalisz.kamil.resultstatehandler.ResultStateHandler;

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
public class WindowManagerImpl implements WindowManager
{
    private ResultStateHandler<String,IntentResult,IntentResultCallback> resutlHandler;

    private ActivityStarter activityStarter;

    @Override
    public void registerIntentHandler(String key, IntentHandler intentHandler) {
        resutlHandler.registerCallback(key,new IntentResultCallback(intentHandler));
    }

    @Override
    public void startActivity(String key, Intent intent, Bundle options) {
        this.activityStarter.startActivity(key,intent,options);
    }

    @Override
    public void startActivity(String key, Intent intent) {
        this.startActivity(key,intent,null);
    }

    @Override
    public int generateRequestCode(String key) {
        return 0;
    }

}
