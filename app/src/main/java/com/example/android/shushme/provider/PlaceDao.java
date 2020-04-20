package com.example.android.shushme.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface PlaceDao {

  @Query("SELECT * FROM places")
  LiveData<List<PlaceEntity>> getAll();

  @Insert
  void add(PlaceEntity place);

  @Delete
  void remove(PlaceEntity place);
}
