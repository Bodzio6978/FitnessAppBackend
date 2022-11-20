package com.gmail.bogumilmecel2.diary_feature.domain.use_case.diary

import com.gmail.bogumilmecel2.diary_feature.domain.model.CaloriesSumResponse
import com.gmail.bogumilmecel2.diary_feature.domain.repository.DiaryRepository

class GetUserCaloriesSum(
    private val diaryRepository: DiaryRepository
) {

    suspend operator fun invoke(
        date:String,
        userId:String
    ):CaloriesSumResponse{
        return diaryRepository.getUserCaloriesSum(
            date = date,
            userId = userId
        ).data ?: CaloriesSumResponse(0)
    }
}