package com.c2info.akhil_systemtest

import android.content.Context
import com.c2info.akhil_systemtest.interfaces.CallBack
import com.c2info.akhil_systemtest.models.Data
import com.c2info.akhil_systemtest.models.RequestUsersList
import com.c2info.akhil_systemtest.models.ResponseUsers
import com.c2info.akhil_systemtest.network.APICall
import com.c2info.akhil_systemtest.room.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

/** Data repository to fetch the image from raw folder */

object DataRepository {

    fun getUsers(pageNumber: Int, context: Context) {
        CoroutineScope(IO).launch {
            APICall.getUsersList(false, null, RequestUsersList(pageNumber), object : CallBack {
                override fun onClick(t: Any?, t2: Any?, t3: Any?) {
                    t?.let {
                        saveToDb((it as ResponseUsers).data as ArrayList<Data>, context)
                    }
                }
            })
        }
    }

    private fun saveToDb(items: ArrayList<Data>, context: Context) {
        CoroutineScope(IO).launch {
            val dao = AppDatabase.getAppDataBase(context)?.getUserDao()
            dao?.insertAll(items)
        }
    }
}