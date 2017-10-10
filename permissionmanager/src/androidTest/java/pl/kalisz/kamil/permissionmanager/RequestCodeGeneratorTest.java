package pl.kalisz.kamil.permissionmanager;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
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
public class RequestCodeGeneratorTest
{

    RequestCodeGenerator requestCodeGenerator;
    StateSaver stateSaver;

    @Before
    public void setUp()
    {
        stateSaver = new StateSaver();
        requestCodeGenerator = new RequestCodeGenerator(stateSaver);
    }

    @Test
    public void whenRequestCodeForSameStringCodeResultIsSame()
    {
        RequestCodeGenerator requestCodeGenerator = new RequestCodeGenerator(new StateSaver());
        String testCode = "TEST_CODE";

        int firstResult = requestCodeGenerator.getRequestCode(testCode);
        int secondResult  = requestCodeGenerator.getRequestCode(testCode);

        Assert.assertEquals(firstResult,secondResult);
    }

    @Test
    public void whenRequestTwoDifferentCodesResultsAreDifferentSame()
    {
        RequestCodeGenerator requestCodeGenerator = new RequestCodeGenerator(new StateSaver());
        String testCode = "TEST_CODE";
        String secondTestCode = "SECOND_TEST_CODE";

        int firstResult = requestCodeGenerator.getRequestCode(testCode);
        int secondResult  = requestCodeGenerator.getRequestCode(secondTestCode);

        Assert.assertNotEquals(firstResult,secondResult);
    }

    @Test
    public void whenRequestCodeSaveAndRestoreAndRequestSameCodeResultIsSame()
    {
        RequestCodeGenerator requestCodeGenerator = new RequestCodeGenerator(new StateSaver());
        String testCode = "TEST_CODE";

        int firstResult = requestCodeGenerator.getRequestCode(testCode);

        Bundle savedState = stateSaver.onSaveState();
        Bundle restored = ParcelUtils.saveAndRestoreBundle(savedState);

        StateSaver restoredStateSaver = new StateSaver();
        restoredStateSaver.onRestoreState(restored);
        RequestCodeGenerator restoredRequestCodeGenerator = new RequestCodeGenerator(restoredStateSaver);
        int secondResult  = restoredRequestCodeGenerator.getRequestCode(testCode);

        Assert.assertEquals(firstResult,secondResult);
    }

    @Test
    public void whenRequestCodeIsGeneratedTagForThisCodeIsEqualsInputTag()
    {
        RequestCodeGenerator requestCodeGenerator = new RequestCodeGenerator(new StateSaver());
        String testCode = "TEST_CODE";

        int firstResult = requestCodeGenerator.getRequestCode(testCode);

        Assert.assertEquals(testCode,requestCodeGenerator.getTagFromRequestCode(firstResult));
    }

    @Test
    public void whenGenerateTagForNotRequestedCodeResultIsNull()
    {
        RequestCodeGenerator requestCodeGenerator = new RequestCodeGenerator(new StateSaver());

        Assert.assertNull(requestCodeGenerator.getTagFromRequestCode(12));
    }
}