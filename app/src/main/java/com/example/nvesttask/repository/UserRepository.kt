package com.example.nvesttask.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nvesttask.model.Product
import com.example.nvesttask.retrofit.ApiClient
import com.example.nvesttask.retrofit.ApiInterface
import com.example.nvesttask.roomdatabase.UserDatabase
import com.example.nvesttask.roomdatabase.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    var coroutineScope = CoroutineScope(SupervisorJob())
    private val isInsertedLiveData = MutableLiveData<Boolean>()
    val isInserted : LiveData<Boolean>
        get() {
            return isInsertedLiveData
        }
    private val isLoggedInLiveData = MutableLiveData<Boolean>()
    val isLoggedIn : LiveData<Boolean>
        get() {
            return isLoggedInLiveData
        }

    private val productLiveData = MutableLiveData<List<Product>>()
    val products : LiveData<List<Product>>
        get() {
            return productLiveData
        }

    fun signUpUser(context: Context, entity: UserEntity) {
        coroutineScope.launch {
             if (UserDatabase.getInstance(context).getUserDetailDao().insert(entity) > 0L) {
                 isInsertedLiveData.postValue(true)
             } else isInsertedLiveData.postValue(false)

        }
    }

    fun loginUser(context: Context, userName: String, password: String) {
        coroutineScope.launch {
            val userInfo =  UserDatabase.getInstance(context).getUserDetailDao().getUser(userName, password)
            if (userInfo != null) {
                isLoggedInLiveData.postValue(true)
            } else isLoggedInLiveData.postValue(false)
        }
    }

    fun getProductList(context: Context) {
        val apiInterface = ApiClient.getInstance().create(ApiInterface::class.java)
        val call = apiInterface.getProductList()
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                Log.e("TAG", "onResponse: "+response.body() )
                productLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e("TAG", "onResponse: onFailure "+t.toString() )
            }
        })
    }


}