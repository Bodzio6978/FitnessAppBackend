package com.gmail.bogumilmecel2.user.user_data.data.table

import com.gmail.bogumilmecel2.user.user_data.domain.model.UserInformationEntity
import org.ktorm.schema.Table
import org.ktorm.schema.double
import org.ktorm.schema.int

object UserInformationTable: Table<UserInformationEntity>(tableName = "userInformation") {
    val id = int("id").primaryKey().bindTo { it.id }
    val activityInADay = int("activityInADay").bindTo { it.activityInADay }
    val typeOfWork = int("typeOfWork").bindTo { it.typeOfWork }
    val workoutInAWeek = int("workoutInAWeek").bindTo { it.workoutInAWeek }
    val gender = int("gender").bindTo { it.gender }
    val height = double("height").bindTo { it.height }
    val currentWeight = double("currentWeight").bindTo { it.currentWeight }
    val wantedWeight = double("wantedWeight").bindTo { it.wantedWeight }
    val age = int("age").bindTo { it.age }
}