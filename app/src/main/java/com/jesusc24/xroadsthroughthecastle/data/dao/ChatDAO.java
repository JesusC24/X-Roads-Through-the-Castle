package com.jesusc24.xroadsthroughthecastle.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jesusc24.xroadsthroughthecastle.data.model.Chat;

import java.util.List;

@Dao
public interface ChatDAO {

    @Insert
    long insert(Chat chat);

    @Update
    void update(Chat chat);

    @Delete
    void delete(Chat chat);

    @Query("delete from chat")
    void deleteAll();

    @Query("select * from chat order by nombre asc")
    List<Chat> select();
}
