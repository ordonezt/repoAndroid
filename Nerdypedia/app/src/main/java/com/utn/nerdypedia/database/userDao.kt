package com.utn.nerdypedia.database

import androidx.room.*
import com.utn.nerdypedia.entities.Scientist
import com.utn.nerdypedia.entities.User

@Dao
interface userDao {
    @Query("SELECT * FROM user ORDER BY id")
    fun loadAllUsers(): MutableList<User?>?

    @Insert
    fun insertUser(user: User?)

    @Update
    fun updateUser(user: User?)

    @Delete
    fun deleteUser(user: User?)

    @Query("SELECT * FROM user WHERE id = :id")
    fun loadUserById(id: Int): User?

    @Query("SELECT * FROM user WHERE username = :username")
    fun loadUserByUsername(username: String): User?
}