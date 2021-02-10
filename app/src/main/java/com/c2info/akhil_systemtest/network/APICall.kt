package com.c2info.akhil_systemtest.network

import android.util.Log
import androidx.fragment.app.FragmentManager
import com.c2info.akhil_systemtest.BuildConfig.DEBUG
import com.c2info.akhil_systemtest.interfaces.CallBack
import com.c2info.akhil_systemtest.models.LoginModel
import com.c2info.akhil_systemtest.models.RequestUsersList
import com.c2info.akhil_systemtest.models.ResponseLogin
import com.c2info.akhil_systemtest.models.ResponseUsers
import com.c2info.akhil_systemtest.utils.MyDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object APICall {

    private fun showConnectionError(fragmentManager: FragmentManager?) {
        fragmentManager?.let {
            MyDialogFragment.newInstance(
                MyDialogFragment.DIALOG_TYPE.ALERT, "Connection Error",
                "Please check your internet connection and try again.", null
            ).show(fragmentManager, "")

        }
    }

    private fun showSomethingWentWrong(fragmentManager: FragmentManager?) {
        fragmentManager?.let {
            MyDialogFragment.newInstance(
                MyDialogFragment.DIALOG_TYPE.ALERT, "Something went wrong!",
                "Please try again.", null
            ).show(fragmentManager, "")
        }
    }

    fun login(
        showProgress: Boolean, fragmentManager: FragmentManager?, reqBody: LoginModel,
        callBack: CallBack
    ) {
        var dialog: MyDialogFragment? = null
        fragmentManager?.let {
            dialog = MyDialogFragment.newInstance(
                MyDialogFragment.DIALOG_TYPE.PROGRESS,
                null, null, null
            )
            if (showProgress) dialog?.show(fragmentManager, "")
        }
        val service =
            RetrofitClientInstance.retrofitInstance?.create(APIInterface::class.java)
        val call = service?.login(reqBody)
        call?.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(
                call: Call<ResponseLogin>,
                response: Response<ResponseLogin>
            ) {
                dialog?.let {
                    if (it.isAdded) {
                        it.dismissAllowingStateLoss()
                    }
                }
                if (response.body() != null) {
                    if (response.body() != null) {
                        callBack.onClick(response.body())
                    } else {
                        showSomethingWentWrong(fragmentManager)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {

                dialog?.let {
                    if (it.isAdded) {
                        it.dismissAllowingStateLoss()
                    }
                }
                if (DEBUG) Log.e("Error", "->" + t.message)
                showConnectionError(fragmentManager)
            }
        })
    }


    fun getUsersList(
        showProgress: Boolean, fragmentManager: FragmentManager?, reqBody: RequestUsersList,
        callBack: CallBack
    ) {
        var dialog: MyDialogFragment? = null
        fragmentManager?.let {
            dialog = MyDialogFragment.newInstance(
                MyDialogFragment.DIALOG_TYPE.PROGRESS,
                null, null, null
            )
            if (showProgress) dialog?.show(fragmentManager, "")
        }
        val service =
            RetrofitClientInstance.retrofitInstance?.create(APIInterface::class.java)
        val call = service?.getUsersList(reqBody)
        call?.enqueue(object : Callback<ResponseUsers> {
            override fun onResponse(
                call: Call<ResponseUsers>,
                response: Response<ResponseUsers>
            ) {
                dialog?.let {
                    if (it.isAdded) {
                        it.dismissAllowingStateLoss()
                    }
                }
                if (response.body() != null) {
                    if (response.body() != null) {
                        callBack.onClick(response.body())
                    } else {
                        showSomethingWentWrong(fragmentManager)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUsers>, t: Throwable) {

                dialog?.let {
                    if (it.isAdded) {
                        it.dismissAllowingStateLoss()
                    }
                }
                if (DEBUG) Log.e("Error", "->" + t.message)
                showConnectionError(fragmentManager)
            }
        })
    }


}
