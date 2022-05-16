package com.utn.nerdypedia.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.utn.nerdypedia.entities.Scientist
import com.utn.nerdypedia.entities.User

@Database(entities = [Scientist::class, User::class], version = 7, exportSchema = false)

public  abstract class appDataBase : RoomDatabase() {

    abstract fun scientistDao(): scientistDao
    abstract fun userDao(): userDao

    companion object {
        var INSTANCE: appDataBase? = null

        fun getAppDataBase(context: Context): appDataBase? {
            if (INSTANCE == null) {
                synchronized(appDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        appDataBase::class.java,
                        "scientistDB"
                    ).allowMainThreadQueries().fallbackToDestructiveMigration().build() // No es lo mas recomendable que se ejecute en el mainthread
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}