package com.gmail.bogumilmecel2.diary_feature.domain.use_case

import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import com.gmail.bogumilmecel2.diary_feature.domain.repository.DiaryRepository

class GetProducts(
    private val repository: DiaryRepository
) {

    suspend operator fun invoke(text:String):List<Product>{
        return repository.getProducts(text)
    }
}