package com.example.android.shushme;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.android.shushme.data.PlaceEntity;
import com.example.android.shushme.data.PlaceRepository;
import java.util.List;

public class PlaceViewModel extends ViewModel {

  void initialize(Context context) {
    PlaceRepository.initialize(context);
  }

  LiveData<List<PlaceEntity>> getAllPlaces() {
    return PlaceRepository.get().getAll();
  }

  void addPlace(PlaceEntity placeEntity) {
    PlaceRepository.get().add(placeEntity);
  }
}
