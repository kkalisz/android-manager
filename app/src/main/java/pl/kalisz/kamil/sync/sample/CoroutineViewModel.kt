package pl.kalisz.kamil.sync.sample

import android.Manifest
import android.app.Application
import android.content.Intent
import pl.kalisz.kamil.dialogmanager.DialogDefinition
import pl.kalisz.kamil.dialogmanager.DialogHandler
import pl.kalisz.kamil.permissionmanager.PermissionHandler
import pl.kalisz.kamil.permissionmanager.PermissionResult

import pl.kalisz.kamil.sync.viewmodel.BaseViewModel
import pl.kalisz.kamil.windowmanager.IntentHandler
import pl.kalisz.kamil.windowmanager.IntentResult
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * Copyright (C) 2016 Kamil Kalisz.
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
open class CoroutineViewModel(application: Application) : BaseViewModel(application) {

    suspend fun requestPermissions(permissions: List<String>) : PermissionResult =
            suspendCoroutine { cont: Continuation<PermissionResult> ->
                requestPermissions(permissions, PermissionHandler { _, permissionResult -> cont.resume(permissionResult) })
            }

    suspend fun startActivity(intent: Intent): IntentResult =
            suspendCoroutine { cont: Continuation<IntentResult> ->
                startActivity(intent, IntentHandler({ requestCode, resultCode, resultData -> cont.resume(IntentResult(requestCode, resultCode, resultData)) }))
            }


    suspend fun <RESULT_TYPE> showDialog(dialogDefinition: DialogDefinition<RESULT_TYPE>): RESULT_TYPE =
            suspendCoroutine { cont: Continuation<RESULT_TYPE> ->
              showDialog(dialogDefinition, DialogHandler({ _, dialogResult -> cont.resume(dialogResult as RESULT_TYPE) }))
            }
}
