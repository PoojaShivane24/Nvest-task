package com.example.nvesttask.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nvesttask.model.Product
import com.example.nvesttask.repository.UserRepository
import com.example.nvesttask.roomdatabase.UserEntity

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    var context : Application = application

    var repo : UserRepository = UserRepository()
    val isInserted : LiveData<Boolean>
        get() {
            return repo.isInserted
        }
    val isLoggedIn : LiveData<Boolean>
        get() {
            return repo.isLoggedIn
        }
    val products : LiveData<List<Product>>
        get() {
            return repo.products
        }

    fun signUpUser(entity: UserEntity) {
        repo.signUpUser(context, entity)
    }

    fun loginUser(userName: String, password: String) {
        repo.loginUser(context, userName, password)
    }

    fun getProductList() {
        repo.getProductList(context)
    }


}