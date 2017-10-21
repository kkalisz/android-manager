package pl.kalisz.kamil.dialogmanager;

import android.support.annotation.NonNull;

import pl.kalisz.kamil.sync.viewmodel.LiveDataManagerDelegate;

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
public class ViewModelDialogManager extends LiveDataManagerDelegate<ViewModelDialogManager.DialogInfo,String,DialogResult> implements DialogHandler, DialogManager{

    public class DialogInfo
    {
        public DialogInfo(@NonNull String requestCode, @NonNull DialogDefinition dialogDefinition) {
            this.requestCode = requestCode;
            this.dialogDefinition = dialogDefinition;
        }

        private String requestCode;
        private DialogDefinition dialogDefinition;

        @NonNull
        public String getRequestCode() {
            return requestCode;
        }

        @NonNull
        public DialogDefinition getDialogDefinition() {
            return dialogDefinition;
        }
    }

    @Override
    public void onDialogResult(String requestCode, Object result) {
        getResultStateHandler().returnResult(requestCode, new DialogResult(requestCode, result));
    }

    @Override
    public void registerDialogHandler(@NonNull String key, @NonNull DialogHandler dialogHandler) {
        getResultStateHandler().registerCallback(key,new DialogResultCallback(dialogHandler));
    }

    @Override
    public void showDialog(@NonNull String key, @NonNull DialogDefinition dialogDefinition) {
        executeAction(key,new DialogInfo(key,dialogDefinition));
    }
}
