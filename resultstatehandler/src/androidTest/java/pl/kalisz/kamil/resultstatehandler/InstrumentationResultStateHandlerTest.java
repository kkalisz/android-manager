package pl.kalisz.kamil.resultstatehandler;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

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

    @Test
    public void whenStateIsRestoredAndThereAreSavedResultsAfterResumeAndRegisterResultIsPropagated()
    {
        ResultStateHandler<String,TestResult,ResultStateCallback<TestResult>> stateHandler =new ResultStateHandler<>();
        ValueHandler<TestResult> testResultValueHandler = new ValueHandler<>();

        TestResult testResult = new TestResult(TEST_VALUE);
        stateHandler.returnResult(TEST_KEY,testResult);

        Parcel parcel = Parcel.obtain();

        Parcelable pendingResultsState = stateHandler.saveState();

        pendingResultsState.writeToParcel(parcel,0);
        parcel.setDataPosition(0);

        PendingResultsState restoredState = PendingResultsState.CREATOR.createFromParcel(parcel);
        parcel.recycle();

        ResultStateHandler<String,TestResult,ResultStateCallback<TestResult>> newStateHandler =new ResultStateHandler<>();
        newStateHandler.restoreState(restoredState);

        TestCallback testCallback = new TestCallback(testResultValueHandler);

        newStateHandler.registerCallback(TEST_KEY,testCallback);
        Assert.assertEquals(0,testResultValueHandler.getHits());

        newStateHandler.onResume();

        Assert.assertEquals(1,testResultValueHandler.getHits());
        Assert.assertEquals(testResult,testResultValueHandler.getLastValue());
    }

    @Test
    public void whenRegisterCallbackResumeAndRestoreStateResultIsPropagated()
    {
        ResultStateHandler<String,TestResult,ResultStateCallback<TestResult>> stateHandler =new ResultStateHandler<>();
        ValueHandler<TestResult> testResultValueHandler = new ValueHandler<>();

        TestResult testResult = new TestResult(TEST_VALUE);
        stateHandler.returnResult(TEST_KEY,testResult);

        Parcel parcel = Parcel.obtain();

        Parcelable pendingResultsState = stateHandler.saveState();

        pendingResultsState.writeToParcel(parcel,0);
        parcel.setDataPosition(0);

        PendingResultsState restoredState = PendingResultsState.CREATOR.createFromParcel(parcel);
        parcel.recycle();

        ResultStateHandler<String,TestResult,ResultStateCallback<TestResult>> newStateHandler =new ResultStateHandler<>();

        TestCallback testCallback = new TestCallback(testResultValueHandler);

        newStateHandler.registerCallback(TEST_KEY,testCallback);
        Assert.assertEquals(0,testResultValueHandler.getHits());

        newStateHandler.onResume();
        newStateHandler.restoreState(restoredState);

        Assert.assertEquals(1,testResultValueHandler.getHits());
        Assert.assertEquals(testResult,testResultValueHandler.getLastValue());
    }
}
