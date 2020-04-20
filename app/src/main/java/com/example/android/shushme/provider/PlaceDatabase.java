package com.example.android.shushme.provider;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = PlaceEntity.class, version = 1)
public abstract class PlaceDatabase extends RoomDatabase {
  public static final String TABLE_NAME = "places";

  public abstract PlaceDao placeDao();
}
