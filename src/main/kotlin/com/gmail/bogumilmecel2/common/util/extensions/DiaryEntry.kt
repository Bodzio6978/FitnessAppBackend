package com.gmail.bogumilmecel2.common.util.extensions

import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.DiaryEntry

fun DiaryEntry.calculateCalories():Int{
    return (this.product.nutritionValues.calories.toDouble()/100.0*this.weight).toInt()
}