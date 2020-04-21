package com.example.android.shushme.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "places")
public class PlaceEntity {
  @NonNull
  @PrimaryKey
  public final String id;

  public final String name;
  public final String address;

  PlaceEntity(String id, String name, String address) {
    this.id = id;
    this.name = name;
    this.address = address;
  }
}
