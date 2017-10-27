package pl.kalisz.kamil.dialogmanager;

import android.Manifest;
import android.arch.lifecycle.Lifecycle;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

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
@RunWith(AndroidJUnit4.class)
public class DialogManagerImplTest {

    TestLifecycleOwner testLifecycleOwner;
    StateSaver stateSaver;
    TestResultHandler stateHandler;

    @Before
    public void before()
    {
        testLifecycleOwner = new TestLifecycleOwner();
        stateSaver = new StateSaver();
        stateHandler = new TestResultHandler(testLifecycleOwner.getLifecycle(),stateSaver);
    }

    @Test
    public void whenShowDialogAndThereIsResultListenerIsInvoked()
    {
        TestDialogRequester testActivityStarter = new TestDialogRequester();
        DialogManagerImpl windowManager = new DialogManagerImpl(testLifecycleOwner.getLifecycle(),stateSaver,testActivityStarter);
        testActivityStarter.setDialogManager(windowManager);
        String testKey = "TEST_KEY";

        final ValueHandler<String> valueHandler = new ValueHandler<>();

        windowManager.registerDialogHandler(testKey, new DialogHandler() {
            @Override
            public void onDialogResult(String requestCode, Object o) {
                valueHandler.addValue(requestCode);

            }
        });

        Assert.assertEquals(0,valueHandler.getHits());

        windowManager.showDialog(testKey, new TestDialogDefinition());

        Assert.assertEquals(0,valueHandler.getHits());

        testLifecycleOwner.getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_RESUME);

        Assert.assertEquals(testKey,valueHandler.getLastValue());
    }
}