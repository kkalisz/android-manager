package pl.kalisz.kamil.dialogmanager;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pl.kalisz.kamil.resultstatehandler.ResultStateHandler;
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
public class DialogManagerImpl implements DialogManager
{
    private ResultStateHandler<String,DialogResult,DialogResultCallback> resultHandler;
    private DialogRequester dialogRequester;

    public DialogManagerImpl(Lifecycle lifecycle, StateSaver stateSaver, DialogRequester dialogRequester) {
        this.dialogRequester = dialogRequester;
        StateSaver internalStateSaver = new StateSaver();
        stateSaver.registerStateHolder("DialogManagerImpl", internalStateSaver);
        resultHandler = new ResultStateHandler<>(lifecycle, internalStateSaver);
    }

    @Override
    public void registerDialogHandler(@NonNull String key, @NonNull DialogHandler dialogHandler) {
        resultHandler.registerCallback(key,new DialogResultCallback(dialogHandler));
    }

    @Override
    public void showDialog(@NonNull String key, @NonNull DialogDefinition dialogDefinition) {
        dialogRequester.requestDialog(key,dialogDefinition);
    }


    public <DIALOG_RESULT, DIALOG_DEFINITION extends DialogDefinition<DIALOG_RESULT>> void returnResult(String requestCode, @Nullable DIALOG_RESULT dialogResult) {
        resultHandler.returnResult(requestCode,new DialogResult(requestCode,dialogResult));
    }
}
