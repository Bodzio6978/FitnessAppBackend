package com.gmail.bogumilmecel2.diary_feature.domain.use_case

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import com.gmail.bogumilmecel2.diary_feature.domain.repository.DiaryRepository

class InsertProduct(
    private val diaryRepository: DiaryRepository
) {

    suspend operator fun invoke(product:Product):Resource<Product>{
        return diaryRepository.insertProduct(product = product)
    }
}