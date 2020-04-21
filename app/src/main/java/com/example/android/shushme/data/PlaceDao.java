package com.example.android.shushme.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface PlaceDao {

  @Query("SELECT * FROM places")
  LiveData<List<PlaceEntity>> getAll();

  @Insert
  void add(PlaceEntity place);

  @Delete
  void remove(PlaceEntity place);

  @Update
  void update(PlaceEntity place);
}
