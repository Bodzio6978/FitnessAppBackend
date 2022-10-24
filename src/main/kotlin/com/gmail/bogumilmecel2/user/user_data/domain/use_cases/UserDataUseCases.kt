package com.gmail.bogumilmecel2.user.user_data.domain.use_cases

data class UserDataUseCases(
    val saveUserNutritionValues: SaveUserNutritionValues,
    val getUserNutritionValues: GetUserNutritionValues,
    val saveUserInformation: SaveUserInformation,
    val getUserInformation: GetUserInformation
)
