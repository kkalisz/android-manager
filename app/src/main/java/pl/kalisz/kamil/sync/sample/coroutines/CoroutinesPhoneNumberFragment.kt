package pl.kalisz.kamil.sync.sample.coroutines

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pl.kalisz.kamil.sync.BaseFragment
import pl.kalisz.kamil.sync.R
import pl.kalisz.kamil.sync.viewmodel.ViewModelWindowManagerExposer
import pl.kalisz.kamil.sync.viewmodel.WindowHelperProxy

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
class CoroutinesPhoneNumberFragment : BaseFragment() {
    private lateinit var pickPhoneViewModel: CoroutinePhoneNumberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pickPhoneViewModel = ViewModelProviders.of(activity).get(CoroutinePhoneNumberViewModel::class.java)
        WindowHelperProxy.mediate(this, windowHelper, ViewModelWindowManagerExposer.expose(pickPhoneViewModel))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.phone_pick_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.pick_phone).setOnClickListener { pickPhoneViewModel.pickPhoneNumber() }
        val phoneText = view.findViewById<TextView>(R.id.phone_number)
        pickPhoneViewModel.phoneNumber.observe(this, Observer { phone -> phoneText.text = phone })
    }
}