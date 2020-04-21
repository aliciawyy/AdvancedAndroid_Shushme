package com.example.android.shushme.data;

import com.google.android.libraries.places.api.model.Place;

public class PlaceUtils {
  private PlaceUtils() {}

  public static PlaceEntity toPlaceEntity(Place place) {
    return new PlaceEntity(place.getId(), place.getName(), place.getAddress());
  }
}
