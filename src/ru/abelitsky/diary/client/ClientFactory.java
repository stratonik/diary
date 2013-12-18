package ru.abelitsky.diary.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

import ru.abelitsky.diary.client.views.MainView;

public interface ClientFactory {

	public EventBus getEventBus();
	public MainView getMainView();
	public PlaceController getPlaceController();

}
