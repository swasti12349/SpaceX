package com.sro.spacex;
 import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import static androidx.room.OnConflictStrategy.REPLACE;

    @Dao
    public interface spaceXDao {

        @Insert(onConflict = REPLACE)
        void insert(Model roomData);


        @Delete
        void reset(List<Model> roomData);


        @Query("SELECT * FROM table_name")
        List<Model> getAll();
    }
