package pl.kalisz.kamil.resultstatehandler;

import junit.framework.Assert;

import org.junit.Test;

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
    @Test

    public void whenRegisterResultAndThereIsNoCallbackResultIsNotPropagated()
    {
        ResultStateHandler<String,Object,ResultStateCallback<Object>> stateHandler =new ResultStateHandler<>();

        TestResult testResult = new TestResult("TEST");
        stateHandler.returnResult("TEST_KEY",testResult);

    }

    @Test
    public void whenHandlerIsResumedRegisterResultAndThereIsCallbackResultIsPropagated()
    {
        ResultStateHandler<String,TestResult,ResultStateCallback<TestResult>> stateHandler =new ResultStateHandler<>();
        stateHandler.onResume();

        TestResult testResult = new TestResult("TEST");
        stateHandler.returnResult("TEST_KEY",testResult);
        ValueHandler<TestResult> testResultValueHandler = new ValueHandler<>();
        TestCallback testCallback = new TestCallback(testResultValueHandler);
        stateHandler.registerCallback("TEST_KEY",testCallback);

        Assert.assertEquals(1,testResultValueHandler.getHits());
        Assert.assertEquals(testResult,testResultValueHandler.getLastValue());

    }

    @Test
    public void whenHandlerIsResumedRegisterCallbackAndThereIsResultResultIsPropagated()
    {
        ResultStateHandler<String,TestResult,ResultStateCallback<TestResult>> stateHandler =new ResultStateHandler<>();
        stateHandler.onResume();
        ValueHandler<TestResult> testResultValueHandler = new ValueHandler<>();
        TestCallback testCallback = new TestCallback(testResultValueHandler);

        stateHandler.registerCallback("TEST_KEY",testCallback);
        Assert.assertEquals(0,testResultValueHandler.getHits());
        TestResult testResult = new TestResult("TEST");
        stateHandler.returnResult("TEST_KEY",testResult);

        Assert.assertEquals(1,testResultValueHandler.getHits());
        Assert.assertEquals(testResult,testResultValueHandler.getLastValue());
    }

    @Test
    public void whenHandlerIsResumedRegisterCallbackAndThereIsNoResultResultIsNotPropagated()
    {
        ResultStateHandler<String,TestResult,ResultStateCallback<TestResult>> stateHandler =new ResultStateHandler<>();
        stateHandler.onResume();
        ValueHandler<TestResult> testResultValueHandler = new ValueHandler<>();
        TestCallback testCallback = new TestCallback(testResultValueHandler);

        stateHandler.registerCallback("TEST_KEY",testCallback);
        Assert.assertEquals(0,testResultValueHandler.getHits());
    }

    @Test
    public void whenHandlerIsNoResumedRegisterResultAndThereIsCallbackNoResultIsPropagated()
    {
        ResultStateHandler<String,TestResult,ResultStateCallback<TestResult>> stateHandler =new ResultStateHandler<>();

        TestResult testResult = new TestResult("TEST");
        stateHandler.returnResult("TEST_KEY",testResult);

        ValueHandler<TestResult> testResultValueHandler = new ValueHandler<>();
        TestCallback testCallback = new TestCallback(testResultValueHandler);
        stateHandler.registerCallback("TEST_KEY",testCallback);

        Assert.assertEquals(0,testResultValueHandler.getHits());
    }

    @Test
    public void whenHandlerIsNotResumedRegisterCallbackAndThereIsResultNoResultIsPropagated()
    {
        ResultStateHandler<String,TestResult,ResultStateCallback<TestResult>> stateHandler =new ResultStateHandler<>();
        ValueHandler<TestResult> testResultValueHandler = new ValueHandler<>();
        TestCallback testCallback = new TestCallback(testResultValueHandler);

        stateHandler.registerCallback("TEST_KEY",testCallback);
        Assert.assertEquals(0,testResultValueHandler.getHits());
        TestResult testResult = new TestResult("TEST");
        stateHandler.returnResult("TEST_KEY",testResult);

        Assert.assertEquals(0,testResultValueHandler.getHits());
    }

    @Test
    public void whenHandlerIsNotResumedRegisterCallbackAndThereIsNoResultResultIsNotPropagated()
    {
        ResultStateHandler<String,TestResult,ResultStateCallback<TestResult>> stateHandler =new ResultStateHandler<>();
        ValueHandler<TestResult> testResultValueHandler = new ValueHandler<>();
        TestCallback testCallback = new TestCallback(testResultValueHandler);

        stateHandler.registerCallback("TEST_KEY",testCallback);
        Assert.assertEquals(0,testResultValueHandler.getHits());
    }

    @Test
    public void whenHandlerIsNotResumedRegisterCallbackAndThereIsResultResultIsPropagatedAfterResume()
    {
        ResultStateHandler<String,TestResult,ResultStateCallback<TestResult>> stateHandler =new ResultStateHandler<>();
        ValueHandler<TestResult> testResultValueHandler = new ValueHandler<>();
        TestCallback testCallback = new TestCallback(testResultValueHandler);

        stateHandler.registerCallback("TEST_KEY",testCallback);
        Assert.assertEquals(0,testResultValueHandler.getHits());
        TestResult testResult = new TestResult("TEST");
        stateHandler.returnResult("TEST_KEY",testResult);

        Assert.assertEquals(0,testResultValueHandler.getHits());

        stateHandler.onResume();

        Assert.assertEquals(1,testResultValueHandler.getHits());
        Assert.assertEquals(testResult,testResultValueHandler.getLastValue());
    }
}