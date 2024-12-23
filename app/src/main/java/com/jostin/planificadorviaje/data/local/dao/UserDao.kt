package com.jostin.planificadorviaje.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jostin.planificadorviaje.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun validateUser(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("DELETE FROM users WHERE email = :email")
    suspend fun deleteUserByEmail(email: String)

    @Query("DELETE FROM users")
    suspend fun clearUsers()

    @Update
    suspend fun updateUser(user: User)

    @Query("UPDATE users SET name = :name WHERE id = :id")
    suspend fun updateUserName(id: String, name: String)

    @Query("UPDATE users SET email = :email WHERE id = :id")
    suspend fun updateUserEmail(id: String, email: String)

}
