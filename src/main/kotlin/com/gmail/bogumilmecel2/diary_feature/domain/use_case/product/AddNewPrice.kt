package com.gmail.bogumilmecel2.diary_feature.domain.use_case.product

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.model.price.Price
import com.gmail.bogumilmecel2.diary_feature.domain.repository.DiaryRepository

class AddNewPrice(
    private val diaryRepository: DiaryRepository
) {

    suspend operator fun invoke(
        productId:String,
        price: Price
    ):Resource<Price>{
        return diaryRepository.addNewPrice(
            productId = productId,
            price = price
        )
    }
}