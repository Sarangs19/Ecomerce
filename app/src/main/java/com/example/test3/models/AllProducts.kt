package com.example.test3.models

data class AllProducts(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)