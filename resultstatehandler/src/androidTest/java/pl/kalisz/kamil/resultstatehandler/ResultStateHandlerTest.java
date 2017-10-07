package pl.kalisz.kamil.resultstatehandler;

import android.arch.lifecycle.Lifecycle;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

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
public class ResultStateHandlerTest
{

    private static final String TEST_KEY = "TEST_KEY";
    private static final String TEST_VALUE = "TEST";

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
    public void whenRegisterResultAndThereIsNoCallbackResultIsNotPropagated()
    {
        TestResult testResult = new TestResult("TEST");
        stateHandler.returnResult(TEST_KEY,testResult);
    }

    @Test
    public void whenHandlerIsResumedRegisterResultAndThereIsCallbackResultIsPropagated()
    {
        testLifecycleOwner.getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_RESUME);

        TestResult testResult = new TestResult("TEST");
        stateHandler.returnResult(TEST_KEY,testResult);
        TestCallback testCallback = new TestCallback();
        stateHandler.registerCallback(TEST_KEY,testCallback);

        Assert.assertEquals(1,testCallback.getValueHandler().getHits());
        Assert.assertEquals(testResult,testCallback.getValueHandler().getLastValue());

    }

    @Test
    public void whenHandlerIsResumedRegisterCallbackAndThereIsResultResultIsPropagated()
    {
        testLifecycleOwner.getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        TestCallback testCallback = new TestCallback();

        stateHandler.registerCallback(TEST_KEY,testCallback);
        Assert.assertEquals(0,testCallback.getValueHandler().getHits());
        TestResult testResult = new TestResult(TEST_VALUE);
        stateHandler.returnResult(TEST_KEY,testResult);

        Assert.assertEquals(1,testCallback.getValueHandler().getHits());
        Assert.assertEquals(testResult,testCallback.getValueHandler().getLastValue());
    }

    @Test
    public void whenHandlerIsResumedRegisterCallbackAndThereIsNoResultResultIsNotPropagated()
    {
        testLifecycleOwner.getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        TestCallback testCallback = new TestCallback();

        stateHandler.registerCallback(TEST_KEY,testCallback);
        Assert.assertEquals(0,testCallback.getValueHandler().getHits());
    }

    @Test
    public void whenHandlerIsNoResumedRegisterResultAndThereIsCallbackNoResultIsPropagated()
    {
        TestResult testResult = new TestResult("TEST");
        stateHandler.returnResult(TEST_KEY,testResult);

        TestCallback testCallback = new TestCallback();
        stateHandler.registerCallback(TEST_KEY,testCallback);

        Assert.assertEquals(0,testCallback.getValueHandler().getHits());
    }

    @Test
    public void whenHandlerIsNotResumedRegisterCallbackAndThereIsResultNoResultIsPropagated()
    {
        TestCallback testCallback = new TestCallback();

        stateHandler.registerCallback(TEST_KEY,testCallback);
        Assert.assertEquals(0,testCallback.getValueHandler().getHits());
        TestResult testResult = new TestResult("TEST");
        stateHandler.returnResult(TEST_KEY,testResult);

        Assert.assertEquals(0,testCallback.getValueHandler().getHits());
    }

    @Test
    public void whenHandlerIsNotResumedRegisterCallbackAndThereIsNoResultResultIsNotPropagated()
    {
        TestCallback testCallback = new TestCallback();

        stateHandler.registerCallback(TEST_KEY,testCallback);
        Assert.assertEquals(0,testCallback.getValueHandler().getHits());
    }

    @Test
    public void whenHandlerIsNotResumedRegisterCallbackAndThereIsResultResultIsPropagatedAfterResume()
    {
        TestCallback testCallback = new TestCallback();

        stateHandler.registerCallback(TEST_KEY,testCallback);
        Assert.assertEquals(0,testCallback.getValueHandler().getHits());
        TestResult testResult = new TestResult("TEST");
        stateHandler.returnResult(TEST_KEY,testResult);

        Assert.assertEquals(0,testCallback.getValueHandler().getHits());

        testLifecycleOwner.getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_RESUME);

        Assert.assertEquals(1,testCallback.getValueHandler().getHits());
        Assert.assertEquals(testResult,testCallback.getValueHandler().getLastValue());
    }
}