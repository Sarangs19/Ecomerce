package com.example.test3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test3.dbHelpers.ProductDatabase
import com.example.test3.dbHelpers.ProductDb
import com.example.test3.models.AllProducts
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel : MainViewModel
    private lateinit var productRepository : ProductRepository
    private lateinit var productDatabase: ProductDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productDatabase = ProductDatabase.getDatabase(this)
        val productApi = RetrofitHelper.getInstance().create(ApiService::class.java)
        productRepository = ProductRepository(productApi, applicationContext, productDatabase)
        mainViewModel  = ViewModelProvider(this, MainViewModelFactory(productRepository))[MainViewModel::class.java]

        mainViewModel.viewModelScope.launch {
            productDatabase.productDao().insertProduct(ProductDb(0,"Hye","GM"))
        }

        setContent {
            val prod = mainViewModel._products.collectAsState()
            DisplayProduct(prod,
                 modifier =  Modifier.padding(10.dp)
            ) {
                mainViewModel.viewModelScope.launch {
                    addProduct(prod)
                }
            }

        }
    }

    suspend fun addProduct(prod: State<AllProducts>) {
        prod.value.products.forEach({
            productDatabase.productDao().insertProduct(ProductDb(0,it.title,it.description))
        })


    }
}

@Composable
fun DisplayProduct(allProducts: State<AllProducts>, modifier: Modifier, onClick : () -> Unit){
    Column {
        Button(
            onClick = {
                onClick()

            },
        ) {
            Text(text = "Add Product")
        }
        if (allProducts == null) {
            Text(text = "Loading...", modifier)
        } else {
            Column {
                allProducts.value.products.forEach {
                    Text(text = it.title, modifier)
                }
            }
        }
    }
}




