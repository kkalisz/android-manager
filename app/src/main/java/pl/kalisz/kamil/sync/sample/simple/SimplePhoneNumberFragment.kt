package pl.kalisz.kamil.sync.sample.simple

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import pl.kalisz.kamil.dialogmanager.AlertDialogDefinition
import pl.kalisz.kamil.dialogmanager.AlertDialogResult
import pl.kalisz.kamil.statesaver.SavedValue
import pl.kalisz.kamil.sync.BaseFragment
import pl.kalisz.kamil.sync.R
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
internal const val PERMISSION_REQUEST_CODE = "PHONE_PERMISSION"
internal const val CONFIRMATION_DIALOG_REQUEST_CODE = "CONFIRMATION_DIALOG"
internal const val PHONE_PICK_REQUEST_CODE = "PHONE_PICK_REQUEST_CODE"


class SimplePhoneNumberFragment : BaseFragment() {

    private var phoneNumber: SavedValue<String?> = SavedValue()
    private var phoneNumberCandidate: SavedValue<String?> = SavedValue()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.phone_pick_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        windowHelper.stateSaver.registerStateHolder("PHONE_STATE_SAVER",phoneNumber)
        windowHelper.stateSaver.registerStateHolder("PHONE_CANDIDATE_STATE_SAVER",phoneNumberCandidate)

        val phoneView = view!!.findViewById<TextView>(R.id.phone_number)
        if(phoneNumber.value != null) {
            phoneView.text = phoneNumber.value
        }
        view!!.findViewById<Button>(R.id.pick_phone).setOnClickListener({
            windowHelper.permissionManager.requestPermissions(PERMISSION_REQUEST_CODE, listOf(Manifest.permission.READ_CONTACTS)) })

        windowHelper.permissionManager.registerPermissionHandler(PERMISSION_REQUEST_CODE, { _, permissionResult ->
            if(permissionResult.wasAllPermissionsGranted()) {
                windowHelper.windowManager.startActivity(PHONE_PICK_REQUEST_CODE, createPickPhoneIntent())
            }
        })
        windowHelper.windowManager.registerIntentHandler(PHONE_PICK_REQUEST_CODE, { _, resultCode, resultData ->
            phoneNumberCandidate.value = extractPhoneFromResult(activity,resultCode,resultData)
            if(phoneNumberCandidate.value != null) {
                val dialogDefinition = AlertDialogDefinition("Czy Napewno chcesz wykonac przelew na wybrany numer telefonu ?", phoneNumberCandidate.value, "TAK", "NIE")
                windowHelper.dialogManager.showDialog(CONFIRMATION_DIALOG_REQUEST_CODE,dialogDefinition)
            }
        })
        windowHelper.dialogManager.registerDialogHandler(CONFIRMATION_DIALOG_REQUEST_CODE, { _, dialog_result ->
            if(dialog_result == AlertDialogResult.POSITIVE) {
                phoneNumber.value = phoneNumberCandidate.value
                phoneNumberCandidate.value = null
                phoneView.text = phoneNumber.value
            }
        })
    }
}
