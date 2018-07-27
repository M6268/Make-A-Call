package com.example.shanoop.mediaplayer

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.makeCall

class MainActivity : AppCompatActivity() {

    var gotPermit=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Dexter.withActivity(this).
                withPermissions(Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS).
                withListener(object:MultiplePermissionsListener
                {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if(report!!.areAllPermissionsGranted())
                            gotPermit=true
                        else
                            gotPermit=false
                        if(report!=null)
                        {
                            for (i in 0..report.grantedPermissionResponses.size -1)
                            {
                                Log.w("-->","${report.grantedPermissionResponses[i].permissionName}")
                            }

                            for (i in 0..report.deniedPermissionResponses.size -1)
                            {
                                Log.w("<--","${report.deniedPermissionResponses[i].permissionName}")
                            }
                        }
                        else
                            gotPermit=false
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                        Log.w("WARN","DENIED")
                    }

                }).check()
    }

    fun callNow(v:View)
    {
        if(gotPermit)
            makeCall(phoneEditText.text.toString())
        else
            longToast("run time permission required")
    }
}
