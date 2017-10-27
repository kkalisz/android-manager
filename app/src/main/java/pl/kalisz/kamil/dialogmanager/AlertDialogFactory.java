package pl.kalisz.kamil.dialogmanager;

import android.content.Context;
import android.support.v4.app.DialogFragment;

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
public class AlertDialogFactory implements RegistryDialogRequester.DialogFactory<AlertDialogDefinition> {

    @Override
    public DialogFragment createFragment(String requestCode, Context context, AlertDialogDefinition dialogDefinition) {
        return AlertDialogFragment.newInstance(dialogDefinition);
    }
}
