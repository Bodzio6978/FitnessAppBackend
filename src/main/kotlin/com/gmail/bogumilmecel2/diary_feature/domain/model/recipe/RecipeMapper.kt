package com.gmail.bogumilmecel2.diary_feature.domain.model.recipe

import com.gmail.bogumilmecel2.common.util.extensions.toObjectId
import org.bson.types.ObjectId

fun Recipe.toDto(userId: ObjectId): RecipeDto = RecipeDto(
    _id = id.toObjectId(),
    name = name,
    ingredients = ingredients,
    timestamp = timestamp,
    imageUrl = imageUrl,
    timeNeeded = timeNeeded,
    difficulty = difficulty,
    servings = servings,
    userId = userId,
    username = username
)

fun RecipeDto.toObject(): Recipe = Recipe(
    id = _id.toString(),
    name = name,
    ingredients = ingredients,
    timestamp = timestamp,
    imageUrl = imageUrl,
    timeNeeded = timeNeeded,
    difficulty = difficulty,
    servings = servings,
    username = username
)