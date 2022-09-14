package com.gmail.bogumilmecel2.diary_feature.domain.use_case.diary

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.DiaryEntry
import com.gmail.bogumilmecel2.diary_feature.domain.repository.DiaryRepository

class InsertDiaryEntry(
    private val diaryRepository: DiaryRepository
) {

    suspend operator fun invoke(
        diaryEntry: DiaryEntry,
        userId:Int
    ):Resource<DiaryEntry>{
        return diaryRepository.insertDiaryEntry(
            diaryEntry = diaryEntry,
            userId = userId
        )
    }
}