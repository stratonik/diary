package ru.abelitsky.diary.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class MainPlace extends Place {

	public static class Tokenizer implements PlaceTokenizer<MainPlace> {

		@Override
		public MainPlace getPlace(String token) {
			return new MainPlace();
		}

		@Override
		public String getToken(MainPlace place) {
			return null;
		}

	}

}
