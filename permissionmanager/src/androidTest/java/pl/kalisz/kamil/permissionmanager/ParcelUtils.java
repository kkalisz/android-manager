package pl.kalisz.kamil.permissionmanager;

import android.os.Bundle;
import android.os.Parcel;

import pl.kalisz.kamil.statesaver.StateSaver;

public class ParcelUtils
{
    public static Bundle saveAndRestoreBundle(Bundle bundleToSave)
    {
        bundleToSave.setClassLoader(StateSaver.class.getClassLoader());
        Parcel parcel = Parcel.obtain();
        bundleToSave.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        Bundle restoreState = parcel.readBundle(StateSaver.class.getClassLoader());
        parcel.recycle();
        return restoreState;
    }

}