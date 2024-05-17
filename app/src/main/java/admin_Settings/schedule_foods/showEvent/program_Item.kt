package com.example.myapp.recycleView

import kotlinx.serialization.Serializable

@Serializable
data class program_Item(
    val storageID: String,
    val nodeIDEvent: String,
    val startDayTimeStamp: Long,
    val endDayTimeStamp: Long,
    val menuList: String,
    val sliderList: String
)
