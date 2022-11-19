package com.gmail.bogumilmecel2.diary_feature.domain.use_case.diary

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.DiaryEntryDto
import com.gmail.bogumilmecel2.diary_feature.domain.repository.DiaryRepository

class GetDiaryEntries(
    private val diaryRepository: DiaryRepository
) {

    suspend operator fun invoke(
        date:String,
        userId:String
    ):Resource<List<DiaryEntryDto>>{
        return diaryRepository.getDiaryEntries(
            date = date,
            userId = userId
        )
    }
}