package pl.kalisz.kamil.sync.sample.coroutines

import android.Manifest
import android.app.Application
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import pl.kalisz.kamil.dialogmanager.AlertDialogDefinition
import pl.kalisz.kamil.dialogmanager.AlertDialogResult
import pl.kalisz.kamil.sync.createPickPhoneIntent
import pl.kalisz.kamil.sync.extractPhoneFromResult
import pl.kalisz.kamil.sync.sample.CoroutineViewModel

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
class CoroutinePhoneNumberViewModel(application: Application): CoroutineViewModel(application)
{
    val phoneNumber = MutableLiveData<String>()

    fun pickPhoneNumber()
    {
       launch(UI) {
            val permissionResult = requestPermissions(listOf(Manifest.permission.READ_CONTACTS))
            if(permissionResult.wasAllPermissionsGranted()) {
                val result = startActivity(createPickPhoneIntent())
                val phone = extractPhoneFromResult(getApplication(),result.resultCode,result.resultData)
                if(phone != null){
                    val dialogDefinition = AlertDialogDefinition("Czy Napewno chcesz wykonac przelew na wybrany numer telefonu ?", phone, "TAK", "NIE")
                    val dialogResult = showDialog(dialogDefinition)
                    if(dialogResult == AlertDialogResult.POSITIVE) {
                        phoneNumber.value = phone
                    }
                }
            }
        }
    }
}