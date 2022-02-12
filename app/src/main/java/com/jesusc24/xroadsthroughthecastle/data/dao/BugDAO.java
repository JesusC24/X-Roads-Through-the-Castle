package com.jesusc24.xroadsthroughthecastle.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jesusc24.xroadsthroughthecastle.data.model.Bug;

import java.util.List;

@Dao
public interface BugDAO {

    @Insert
    long insert(Bug bug);

    @Update
    void update(Bug bug);

    @Delete
    void delete(Bug bug);

    @Query("delete from bug")
    void deleteAll();

    @Query("select * from bug order by nombre asc")
    List<Bug> select();

    @Query("select * from bug")
    List<Bug> hayBugs();

}
