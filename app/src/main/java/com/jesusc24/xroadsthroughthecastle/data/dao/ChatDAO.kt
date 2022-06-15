package com.jesusc24.xroadsthroughthecastle.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.jesusc24.xroadsthroughthecastle.data.model.Chat;

import java.util.List;

@Dao
public interface ChatDAO {

    @Insert
    long insert(Chat chat);

    @Query("delete from chat where id = :id")
    void delete(String id);

    @Query("select * from chat order by nombre asc")
    List<Chat> select();
}
