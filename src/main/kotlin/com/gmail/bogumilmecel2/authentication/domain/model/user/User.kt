package com.gmail.bogumilmecel2.authentication.domain.model.user

import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import com.gmail.bogumilmecel2.user.log.domain.model.LogEntry
import com.gmail.bogumilmecel2.user.user_data.domain.model.UserInformation
import com.gmail.bogumilmecel2.user.weight.domain.model.WeightEntry
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String = "",
    val username:String = "",
    val nutritionValues: NutritionValues? = null,
    val userInformation: UserInformation? = null,
    val latestLogEntry: LogEntry? = null,
    val weightEntries: List<WeightEntry> = emptyList()
)
