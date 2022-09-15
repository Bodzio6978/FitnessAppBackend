package com.gmail.bogumilmecel2.diary_feature.domain.use_case.diary

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.repository.DiaryRepository

class DeleteDiaryEntry(
    private val diaryRepository: DiaryRepository
) {

    suspend operator fun invoke(
        diaryEntryId:Int,
        userId:Int
    ):Resource<Boolean>{
        return diaryRepository.deleteDiaryEntry(
            diaryEntryId = diaryEntryId,
            userId = userId
        )
    }
}