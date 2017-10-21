package pl.kalisz.kamil.dialogmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import java.util.HashMap;

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
public class RegistryDialogRequester implements DialogRequester
{
    public interface DialogFactory<DIALOG_DEFINITION extends DialogDefinition>
    {
        DialogFragment createFragment(String requestCode,Context context, DIALOG_DEFINITION dialogDefinition);
    }

    private HashMap<Class<? extends DialogDefinition>,DialogFactory> dialogFactories = new HashMap<>();

    private Context context;
    private FragmentManager fragmentManager;

    public RegistryDialogRequester(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void requestDialog(@NonNull String requestCode, DialogDefinition dialogDefinition) {
        DialogFactory dialogFactory = dialogFactories.get(dialogDefinition.getClass());
        if(dialogFactory == null)
        {
            throw new IllegalStateException("There is no dialog factory for: "+dialogDefinition.getClass());
        }
        DialogFragment dialogFragment = dialogFactory.createFragment(requestCode,context, dialogDefinition );
        dialogFragment.show(fragmentManager,requestCode);
    }

    public void registerDialogFactory(Class<? extends DialogDefinition> dialogClass, DialogFactory dialogFactory)
    {
        dialogFactories.put(dialogClass,dialogFactory);
    }
}
