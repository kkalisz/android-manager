package pl.kalisz.kamil.sync.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import pl.kalisz.kamil.sync.R;

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
public class CommonFragmentActivity extends AppCompatActivity
{

    private static final String FRAGMENT_CLASS = "FRAGMENT_CLASS";

    public static Intent createIntent(Context context, Class<? extends Fragment> fragmentClass)
    {
        Intent intent = new Intent(context,CommonFragmentActivity.class);
        intent.putExtra(FRAGMENT_CLASS,fragmentClass);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        if(savedInstanceState == null)
        {
            Class<? extends Fragment> fragmentClass = (Class<? extends Fragment>) getIntent().getSerializableExtra(FRAGMENT_CLASS);
           Fragment contentFragment =  Fragment.instantiate(this,fragmentClass.getName());
           getSupportFragmentManager().beginTransaction().add(R.id.fragment,contentFragment).commit();
        }
    }
}
