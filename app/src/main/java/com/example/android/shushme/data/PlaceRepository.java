package com.example.android.shushme.data;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PlaceRepository {
  private static PlaceRepository sInstance;
  private final PlaceDao mPlaceDao;
  private final Executor mExecutor;

  private PlaceRepository(Context context) {
    PlaceDatabase placeDatabase = Room.databaseBuilder(
        context, PlaceDatabase.class, PlaceDatabase.TABLE_NAME).build();
    mPlaceDao = placeDatabase.placeDao();
    mExecutor = Executors.newSingleThreadExecutor();
  }

  public static void initialize(Context context) {
    assert sInstance == null: "PlaceRepository is already initialized!";
    sInstance = new PlaceRepository(context);
  }

  public static PlaceRepository get() {
    assert sInstance != null: "PlaceRepository is not yet initialized!";
    return sInstance;
  }

  public LiveData<List<PlaceEntity>> getAll() {
    return mPlaceDao.getAll();
  }

  public void add(PlaceEntity placeEntity) {
    mExecutor.execute(() -> mPlaceDao.add(placeEntity));
  }

  public void update(PlaceEntity placeEntity) {
    mExecutor.execute(() -> mPlaceDao.update(placeEntity));
  }

  public void remove(PlaceEntity placeEntity) {
    mExecutor.execute(() -> mPlaceDao.remove(placeEntity));
  }
}
