package com.example.nvesttask.roomdatabase

import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: UserEntity) : Long

    @Query("Select * from UserEntity where userName = :userName AND password = :password")
    fun getUser(userName : String, password : String) : UserEntity?
}