package com.utn.nerdypedia.database

import androidx.room.*
import com.utn.nerdypedia.entities.Scientist

@Dao
public interface scientistDao {

    @Query("SELECT * FROM scientist ORDER BY id")
    fun loadAllScientist(): MutableList<Scientist?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertScientist(scientist: Scientist?)

    @Update
    fun updateScientist(scientist: Scientist?)

    @Delete
    fun deleteScientist(scientist: Scientist?)

    @Query("SELECT * FROM scientist WHERE id = :id")
    fun loadScientistById(id: Int): Scientist?

}