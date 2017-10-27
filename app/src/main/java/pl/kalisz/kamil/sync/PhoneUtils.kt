package pl.kalisz.kamil.sync

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.provider.ContactsContract

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

fun createPickPhoneIntent() : Intent
{
    return Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
}

fun extractPhoneFromResult(context: Context, resultCode: Int, data: Intent?): String? {
    if (resultCode == Activity.RESULT_OK && data!= null) {
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(data.data, null, null, null, null)
        cursor!!.moveToFirst()


        val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
        // column index of the contact name
        val pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                arrayOf<String>(id), null)

        pCur.moveToNext()

        val phone = pCur.getString(
                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
        pCur.close()
        return phone
    }
    return null
}