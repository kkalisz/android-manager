package pl.kalisz.kamil.windowmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
public class IntentInfo
{
    private String requestCode;
    private Intent intent;
    private Bundle options;

    public IntentInfo(@NonNull String requestCode,@NonNull Intent intent,@Nullable Bundle options) {
        this.requestCode = requestCode;
        this.intent = intent;
        this.options = options;
    }

    @NonNull
    public String getRequestCode() {
        return requestCode;
    }

    @NonNull
    public Intent getIntent() {
        return intent;
    }

    @Nullable
    public Bundle getOptions() {
        return options;
    }
}
