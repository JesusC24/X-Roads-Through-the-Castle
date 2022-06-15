package com.jesusc24.xroadsthroughthecastle.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jesusc24.xroadsthroughthecastle.data.model.Chat

@Dao
interface ChatDAO {
    @Insert
    fun insert(chat: Chat?): Long

    @Query("delete from chat where id = :id")
    fun delete(id: String?)

    @Query("select * from chat order by nombre asc")
    fun select(): List<Chat?>?
}