package pl.kalisz.kamil.dialogmanager

import android.os.Parcel
import android.os.Parcelable

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
data class AlertDialogDefinition(val title: String?, val message: String?,
                                 val positiveButtonText: String?, val negativeButtonText: String?) : DialogDefinition<AlertDialogResult>, Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(message)
        parcel.writeString(positiveButtonText)
        parcel.writeString(negativeButtonText)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AlertDialogDefinition> {
        override fun createFromParcel(parcel: Parcel): AlertDialogDefinition {
            return AlertDialogDefinition(parcel)
        }

        override fun newArray(size: Int): Array<AlertDialogDefinition?> {
            return arrayOfNulls(size)
        }
    }
}

