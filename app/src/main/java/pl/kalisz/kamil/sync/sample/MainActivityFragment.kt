package pl.kalisz.kamil.sync.sample

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.HandlerContext
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

import java.util.Arrays

import pl.kalisz.kamil.permissionmanager.PermissionHandler
import pl.kalisz.kamil.permissionmanager.PermissionResult
import pl.kalisz.kamil.sync.BaseFragment
import pl.kalisz.kamil.sync.R
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.RestrictsSuspension
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindowHelper().permissionManager.registerPermissionHandler(TEST) { _, permissionResult -> Toast.makeText(activity, "permission result: " + permissionResult.wasAllPermissionsGranted(), Toast.LENGTH_SHORT).show() }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        view!!.findViewById<View>(R.id.button_permission).setOnClickListener { getWindowHelper().permissionManager.requestPermissions(TEST, Arrays.asList(Manifest.permission.READ_CONTACTS)) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val job = async(UI) {
           val a : PermissionResult =  showPermission()
            Toast.makeText(getContext(),a.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun showPermission() : PermissionResult =

        suspendCoroutine<PermissionResult> { cont: Continuation<PermissionResult> ->
            windowHelper.permissionManager.requestPermissions("HELLO", listOf(Manifest.permission.READ_CONTACTS))
            windowHelper.permissionManager.registerPermissionHandler("HELLO", { _, permissionResult -> cont.resume(permissionResult) })

        }

    companion object {

        private val TEST = "TEST"
    }
}
