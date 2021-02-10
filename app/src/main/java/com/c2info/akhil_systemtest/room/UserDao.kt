package com.c2info.akhil_systemtest.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.c2info.akhil_systemtest.models.Data

@Dao
interface UserDao {

    @Query("SELECT * FROM data")
    fun getAll(): LiveData<List<Data>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<Data>)
}