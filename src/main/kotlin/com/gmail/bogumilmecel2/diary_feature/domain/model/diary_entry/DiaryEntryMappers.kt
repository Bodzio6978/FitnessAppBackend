package com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry

import com.gmail.bogumilmecel2.common.util.extensions.toObjectId

fun DiaryEntry.toDto(userId: String): DiaryEntryDto = DiaryEntryDto(
    _id = id.toObjectId(),
    name = name,
    unit = unit,
    timestamp = timestamp,
    userId = userId.toObjectId(),
    nutritionValues = nutritionValues,
    date = date,
    weight = weight,
    mealName = mealName,
    product = product
)

fun DiaryEntryDto.toDiaryEntry(): DiaryEntry = DiaryEntry(
    id = _id.toString(),
    name = name,
    unit = unit,
    timestamp = timestamp,
    nutritionValues = nutritionValues,
    date = date,
    weight = weight,
    mealName = mealName,
    product = product
)