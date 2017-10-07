package pl.kalisz.kamil.resultstatehandler;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
@RunWith(AndroidJUnit4.class)
public class InstrumentationResultStateHandlerTest
{

    public static final String TEST_KEY = "TEST_KEY";
    public static final String TEST_VALUE = "TEST";

    TestLifecycleOwner testLifecycleOwner ;
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
    public void whenRegisterCallbackAndReturnResultNoResultIsPropagatedIfLifecycleIsNotResumed()
    {
        TestCallback testCallback = new TestCallback();

        stateHandler.registerCallback(TEST_KEY,testCallback);
        stateHandler.returnResult(TEST_KEY,new TestResult(TEST_VALUE));

        Assert.assertEquals(0,testCallback.getValueHandler().getHits());
    }

    @Test
    public void whenRegisterCallbackAndReturnResultResultIsPropagatedAfterLifecycleIsResumed()
    {
        TestCallback testCallback = new TestCallback();

        stateHandler.registerCallback(TEST_KEY,testCallback);
        stateHandler.returnResult(TEST_KEY,new TestResult(TEST_VALUE));

        Assert.assertEquals(0,testCallback.getValueHandler().getHits());

        testLifecycleOwner.getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_RESUME);

        Assert.assertEquals(1,testCallback.getValueHandler().getHits());
    }

    @Test
    public void whenReturnResultAndRegisterCallbackResultIsPropagatedAfterLifecycleIsResumed()
    {
        TestCallback testCallback = new TestCallback();

        stateHandler.registerCallback(TEST_KEY,testCallback);
        stateHandler.returnResult(TEST_KEY,new TestResult(TEST_VALUE));

        Assert.assertEquals(0,testCallback.getValueHandler().getHits());

        testLifecycleOwner.getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_RESUME);

        Assert.assertEquals(1,testCallback.getValueHandler().getHits());
    }

    @Test
    public void whenResultIsReturnedAndStateIsSavedAfterRestoreRegisterCallbackWhenLifecycleIsResumedResultIsPropagated()
    {
        TestResult testResult = new TestResult(TEST_VALUE);
        stateHandler.returnResult(TEST_KEY,testResult);

        Bundle savedState = ParcelUtils.saveAndRestoreBundle(stateSaver.onSaveState());

        StateSaver newStateSaver = new StateSaver();
        TestLifecycleOwner newLifecycleOwner = new TestLifecycleOwner();

        TestResultHandler newStateHandler = new TestResultHandler(newLifecycleOwner.getLifecycle(),newStateSaver);

        newStateSaver.onRestoreState(savedState);

        TestCallback testCallback = new TestCallback();

        newStateHandler.registerCallback(TEST_KEY,testCallback);
        Assert.assertEquals(0,testCallback.getValueHandler().getHits());

        newLifecycleOwner.getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        Assert.assertEquals(1,testCallback.getValueHandler().getHits());
        Assert.assertEquals(testResult,testCallback.getValueHandler().getLastValue());
    }

}
