package com.aldi.ganiafoodtestrl.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(val name: String, val image: String, val desc: String) : Parcelable