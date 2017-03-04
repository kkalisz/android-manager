package pl.kalisz.kamil.resultstatehandler;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2016 Kamil Kalisz.
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

/**
 *
 * @param <KEY_TYPE> typ klucza który wiąże ze sobą metody zwtotne z wynikami
 * @param <RESULT_TYPE> typ wyniku do propagacji MUSI BYĆ SERIALIZOWALNY LUB PARCELOWALNY
 * @param <CALLBACK_TYPE> typ metody zwrotnej
 */
public class ResultStateHandler<KEY_TYPE,RESULT_TYPE,CALLBACK_TYPE extends ResultStateCallback<RESULT_TYPE>>
{
    private Map<KEY_TYPE,CALLBACK_TYPE> callbacks = new HashMap<>();
    private Map<KEY_TYPE,RESULT_TYPE> pendingResults = new HashMap<>();
    private boolean resumed;

    /**
     *  rejestruje metode zwrotną, jeśli istnieje wynik dla tej metody
     *  i aktualny stan pozwala na propagację wyniku wynik będzie zwrócony do <code>callback</code>
     * @param key unikalny klucz dla danej metody musi pokrywac sie z kluczem wyniku
     * @param callback metoda zwrotna
     */
    public void registerCallback(@NonNull KEY_TYPE key, @NonNull CALLBACK_TYPE callback)
    {
        callbacks.put(key,callback);
        if(resumed && pendingResults.containsKey(key))
        {
            returnResultInternal(key);
        }
    }

    /**
     *  rejestruje wartosc jeśli istnieje metoda zwrotna dla tej wartości
     *  i aktualny stan pozwala na propagację wyniku wynik będzie zwrócony do metody zwrotnej
     * @param key unikalny klucz dla wyniku metody musi pokrywac sie z kluczem metody zwrotnej
     * @param result wynik do rozpropagowania
     */
    public void returnResult(KEY_TYPE key, RESULT_TYPE result)
    {
        pendingResults.put(key,result);
        if(resumed && callbacks.containsKey(key))
        {
            returnResultInternal(key);
        }
    }

    private void returnResultInternal(KEY_TYPE key) {
        RESULT_TYPE result2 = pendingResults.remove(key);
        callbacks.get(key).callBack(result2);
    }

    public void onResume()
    {
        resumed = true;
        List<KEY_TYPE> savedResults = new ArrayList<>(pendingResults.keySet());
        for(KEY_TYPE key : savedResults)
        {
            if(callbacks.containsKey(key))
            {
                returnResultInternal(key);
            }
        }
    }


    public void onPause()
    {
        resumed = false;
    }

}
