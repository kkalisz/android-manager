package pl.kalisz.kamil.statesaver;

import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;

import static org.junit.Assert.*;

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
@RunWith(AndroidJUnit4.class)
public class StateSaverTest
{

    public static final String MY_VALUE = "My_Value";

    @Test
    public void whenStateIsSaveNewInstanceIsRestoredHolderIsRegisteredThenStateIRestored()
    {
        StateSaver stateSaver = new StateSaver();
        stateSaver.registerStateHolder("SS", new StateHolder() {
            @Nullable
            @Override
            public Object onSaveState() {
                return MY_VALUE;
            }

            @Override
            public void onRestoreState(@Nullable Object restoredState) {

            }
        });

        Bundle savedState = ParcelUtils.saveAndRestoreBundle(stateSaver.onSaveState());

        final CallbackHandler<Serializable> callbackHandler = new CallbackHandler<>();

        final StateSaver restoredSateSaver = new StateSaver();
        restoredSateSaver.onRestoreState(savedState);
        restoredSateSaver.registerStateHolder("SS", new StateHolder<String>() {
            @Nullable
            @Override
            public String onSaveState() {
                return null;
            }

            @Override
            public void onRestoreState(@Nullable String restoredState) {
                callbackHandler.setCallback(restoredState);
            }
        });

        Assert.assertEquals(MY_VALUE,callbackHandler.getLastCallback());
    }

    @Test
    public void whenStateIsSavedNewInstanceCreatedHolderIsRegisteredInstanceIsRestoredStateIRestored()
    {
        StateSaver stateSaver = new StateSaver();
        stateSaver.registerStateHolder("SS", new StateHolder() {
            @Nullable
            @Override
            public Object onSaveState() {
                return MY_VALUE;
            }

            @Override
            public void onRestoreState(@Nullable Object restoredState) {

            }
        });

        Bundle savedState = ParcelUtils.saveAndRestoreBundle(stateSaver.onSaveState());

        final CallbackHandler<Serializable> callbackHandler = new CallbackHandler<>();

        final StateSaver restoredSateSaver = new StateSaver();
        restoredSateSaver.registerStateHolder("SS", new StateHolder<String>() {
            @Nullable
            @Override
            public String onSaveState() {
                return null;
            }

            @Override
            public void onRestoreState(@Nullable String restoredState) {
                callbackHandler.setCallback(restoredState);
            }
        });

        restoredSateSaver.onRestoreState(savedState);

        Assert.assertEquals(MY_VALUE,callbackHandler.getLastCallback());
    }

}