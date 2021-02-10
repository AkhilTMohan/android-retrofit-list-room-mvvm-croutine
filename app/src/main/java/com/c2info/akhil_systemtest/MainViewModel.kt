package com.c2info.akhil_systemtest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

/** View model class which uses livedata scope to get the data from raw file*/

class MainViewModel(private val context: Application) : AndroidViewModel(context) {
    var fetchedOnce = false
    fun getData() = CoroutineScope(IO).launch {
        DataRepository.getUsers(2, context)
    }
}