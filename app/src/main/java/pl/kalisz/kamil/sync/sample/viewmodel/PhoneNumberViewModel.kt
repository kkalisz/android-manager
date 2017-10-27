package pl.kalisz.kamil.sync.sample.viewmodel

import android.Manifest
import android.app.Application
import pl.kalisz.kamil.dialogmanager.AlertDialogDefinition
import pl.kalisz.kamil.permissionmanager.PermissionHandler

import pl.kalisz.kamil.sync.viewmodel.BaseViewModel
import pl.kalisz.kamil.windowmanager.IntentHandler
import android.arch.lifecycle.MutableLiveData
import pl.kalisz.kamil.dialogmanager.AlertDialogResult
import pl.kalisz.kamil.dialogmanager.DialogHandler
import pl.kalisz.kamil.sync.createPickPhoneIntent
import pl.kalisz.kamil.sync.extractPhoneFromResult


/**
 * Copyright (C) 2017 Kamil Kalisz.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
open class PhoneNumberViewModel(application: Application) : BaseViewModel(application) {

    val phoneNumber = MutableLiveData<String>()

    fun pickPhoneNumber() {
        requestPermissions(listOf(Manifest.permission.READ_CONTACTS), PermissionHandler({ _, permissionResult ->
            if (permissionResult.wasAllPermissionsGranted()) {
                startActivity(createPickPhoneIntent(), IntentHandler({ _, resultCode, resultData ->
                    val phone = extractPhoneFromResult(getApplication(),resultCode, resultData)
                    if(phone!=null){
                        val dialogDefinition = AlertDialogDefinition("Czy Napewno chcesz wykonac przelew na wybrany numer telefonu ?", phone, "TAK", "NIE")
                        showDialog(dialogDefinition, DialogHandler { _, dialogResult ->
                            if (dialogResult == AlertDialogResult.POSITIVE) {
                                phoneNumber.value = phone
                            }
                        })
                    }
                }))
            }
        }))
    }

}
