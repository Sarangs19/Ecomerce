package com.example.test3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test3.models.AllProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val productRepository: ProductRepository) : ViewModel() {

     val _products : StateFlow<AllProducts>
        get() = productRepository.allProducts

    init {
         viewModelScope.launch {
            productRepository.getAllProducts()
         }
    }


}