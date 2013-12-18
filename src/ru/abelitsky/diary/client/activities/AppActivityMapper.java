package ru.abelitsky.diary.client.activities;

import ru.abelitsky.diary.client.ClientFactory;
import ru.abelitsky.diary.client.places.MainPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;

	public AppActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof MainPlace) {
			return new MainActivity(clientFactory);
		} else {
			return null;
		}
	}

}
