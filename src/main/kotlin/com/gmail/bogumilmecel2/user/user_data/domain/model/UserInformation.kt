package com.gmail.bogumilmecel2.user.user_data.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInformation (
    val activityInADay:Int = 0,
    val typeOfWork:Int = 0,
    val workoutInAWeek:Int = 0,
    val gender:Int = 0,
    val height:Double = 0.0,
    val currentWeight:Double = 0.0,
    val wantedWeight:Double = 0.0,
    val age:Int = 0
)