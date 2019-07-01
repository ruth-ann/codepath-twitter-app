package com.codepath.apps.restclienttemplate.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SampleModelDao {

    // @Query annotation requires knowing SQL syntax
    // See http://www.sqltutorial.org/

    //ra queries the object by its unique identifier
    @Query("SELECT * FROM SampleModel WHERE id = :id")
    SampleModel byId(long id); //ra the id must match the one in the previous line

    //pulls last 300 items that were created from the table
    @Query("SELECT * FROM SampleModel ORDER BY ID DESC LIMIT 300")
    List<SampleModel> recentItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(SampleModel... sampleModels); //ra if a sample model object is passed in that
    //has a unique identifier that exists on disk, it replaces it with the one that was passed in 2/8 4:30
}

//ra the three @'s are three sql commands