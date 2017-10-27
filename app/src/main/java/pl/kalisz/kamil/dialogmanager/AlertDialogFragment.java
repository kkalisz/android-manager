package pl.kalisz.kamil.dialogmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

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
public class AlertDialogFragment extends DialogFragment implements DialogInterface.OnClickListener
{
    private static String DIALOG_DEFINITION = "DIALOG_DEFINITION";

    public static AlertDialogFragment newInstance(AlertDialogDefinition alertDialogDefinition) {

        Bundle args = new Bundle();
        args.putParcelable(DIALOG_DEFINITION,alertDialogDefinition);
        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private DialogResultPropagator dialogResultPropagator = new DialogResultPropagator(this);

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialogDefinition alertDialogDefinition = getArguments().getParcelable(DIALOG_DEFINITION);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(),getTheme());
        alertDialogBuilder.setTitle(alertDialogDefinition.getTitle()).setMessage(alertDialogDefinition.getMessage())
                .setPositiveButton(alertDialogDefinition.getPositiveButtonText(),this)
                .setNegativeButton(alertDialogDefinition.getNegativeButtonText(), this)
                .setOnCancelListener(this);
        return alertDialogBuilder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dialogResultPropagator.propagateDialogEvent(new DialogResult<>(getTag(), AlertDialogResult.CANCEL));
    }

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        if(which == DialogInterface.BUTTON_POSITIVE)
        {
            dialogResultPropagator.propagateDialogEvent(new DialogResult<>(getTag(), AlertDialogResult.POSITIVE));
        }
        else if(which == DialogInterface.BUTTON_NEGATIVE)
        {
            dialogResultPropagator.propagateDialogEvent(new DialogResult<>(getTag(), AlertDialogResult.NEGATIVE));
        }
    }
}
