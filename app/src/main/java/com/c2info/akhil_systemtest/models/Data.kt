package com.c2info.akhil_systemtest.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Data(
    val avatar: String,
    @PrimaryKey
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String
) : Parcelable