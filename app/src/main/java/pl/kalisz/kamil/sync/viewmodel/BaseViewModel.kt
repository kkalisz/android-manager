package pl.kalisz.kamil.sync.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Intent
import pl.kalisz.kamil.dialogmanager.DialogDefinition
import pl.kalisz.kamil.dialogmanager.DialogHandler
import pl.kalisz.kamil.permissionmanager.PermissionHandler

import pl.kalisz.kamil.sync.WindowHelper
import pl.kalisz.kamil.windowmanager.IntentHandler
import pl.kalisz.kamil.windowmanager.IntentResult
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.suspendCoroutine

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
open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    internal val windowHelper: ViewModelWindowHelper = ViewModelWindowHelper()

    fun getWindowHelper(): WindowHelper {
        return windowHelper
    }

    fun requestPermissions(permissions: List<String>, permissionHandler: PermissionHandler) {
        val tag = System.currentTimeMillis().toString() + permissions.toString()
        windowHelper.permissionManager.requestPermissions(tag, permissions)
        windowHelper.permissionManager.registerPermissionHandler(tag, permissionHandler)
    }

    fun startActivity(intent: Intent, intentHandler: IntentHandler) {

        val tag = System.currentTimeMillis().toString() + intent.toString()
        windowHelper.windowManager.startActivity(tag, intent)
        windowHelper.windowManager.registerIntentHandler(tag, intentHandler)
    }

    fun <RESULT_TYPE> showDialog(dialogDefinition: DialogDefinition<RESULT_TYPE>, dialogHandler: DialogHandler<RESULT_TYPE>) {
        val tag = System.currentTimeMillis().toString() + dialogDefinition.toString()
        windowHelper.dialogManager.showDialog(tag, dialogDefinition)
        windowHelper.dialogManager.registerDialogHandler(tag, dialogHandler)
    }


}
