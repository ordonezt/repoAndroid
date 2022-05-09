package com.utn.nerdypedia.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.utn.nerdypedia.entities.Scientist

@Database(entities = [Scientist::class], version = 4, exportSchema = false)

public  abstract class scientistsDataBase : RoomDatabase() {

    abstract fun scientistDao(): scientistDao

    companion object {
        var INSTANCE: scientistsDataBase? = null

        fun getAppDataBase(context: Context): scientistsDataBase? {
            if (INSTANCE == null) {
                synchronized(scientistsDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        scientistsDataBase::class.java,
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