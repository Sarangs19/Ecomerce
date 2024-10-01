package com.example.test3

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.test3.dbHelpers.ProductDatabase
import com.example.test3.dbHelpers.ProductDb
import com.example.test3.models.AllProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductRepository(
    private val apiService: ApiService,
    private val applicationContext: Context,
    private val productDatabase: ProductDatabase
) {

    private val _allProducts  = MutableStateFlow(AllProducts(limit = 0, products = emptyList(), skip = 0, total = 10))
    private val _allDbProducts  = MutableLiveData<ProductDb>()


    val allProducts : StateFlow<AllProducts>
        get() =  _allProducts

    val allDbProducts : MutableLiveData<ProductDb>
        get() =  _allDbProducts

            suspend fun getAllProducts(){
                if (isOnline(applicationContext )){
                    val result = apiService.getAllProducts()
                    if(result.isSuccessful && result.body() != null){
                        _allProducts.emit(result.body()!!)
                    }
                }else{
                   val list = productDatabase.productDao().getDbProduct().value
                    _allDbProducts.value = list
                }
            }

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}