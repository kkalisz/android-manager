package pl.kalisz.kamil.sync.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import pl.kalisz.kamil.sync.R;
import pl.kalisz.kamil.sync.sample.coroutines.CoroutinesPhoneNumberFragment;
import pl.kalisz.kamil.sync.sample.simple.SimplePhoneNumberFragment;
import pl.kalisz.kamil.sync.sample.viewmodel.PhoneNumberFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void pickerFragment(View view)
    {
        startActivity(CommonFragmentActivity.createIntent(this, SimplePhoneNumberFragment.class));
    }

    public void pickerViewModel(View view)
    {
        startActivity(CommonFragmentActivity.createIntent(this, PhoneNumberFragment.class));
    }

    public void pickerCoroutineViewModel(View view)
    {
        startActivity(CommonFragmentActivity.createIntent(this, CoroutinesPhoneNumberFragment.class));
    }
}
