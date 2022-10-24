package com.gmail.bogumilmecel2.user.user_data.domain.model

import org.ktorm.entity.Entity

interface UserInformationEntity: Entity<UserInformationEntity> {
    val id:Int
    val activityInADay:Int
    val typeOfWork:Int
    val workoutInAWeek:Int
    val gender:Int
    val height:Double
    val currentWeight:Double
    val wantedWeight:Double
    val age:Int
}