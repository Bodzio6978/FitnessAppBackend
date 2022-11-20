package com.gmail.bogumilmecel2.user.weight.domain.model

import com.gmail.bogumilmecel2.common.util.extensions.toObjectId

fun WeightEntryDto.toWeightEntry(): WeightEntry = WeightEntry(
    id = _id.toString(),
    timestamp = timestamp,
    value = value
)

fun WeightEntry.toDto(userId: String): WeightEntryDto = WeightEntryDto(
    _id = id.toObjectId(),
    timestamp = timestamp,
    value = value,
    userId = userId.toObjectId()
)

