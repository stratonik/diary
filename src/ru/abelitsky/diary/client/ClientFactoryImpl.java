package ru.abelitsky.diary.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import ru.abelitsky.diary.client.views.MainView;
import ru.abelitsky.diary.client.views.MainViewImpl;

public class ClientFactoryImpl implements ClientFactory {

	private EventBus eventBus = new SimpleEventBus();
	private PlaceController placeController = new PlaceController(eventBus);

	private MainView mainView;

	public EventBus getEventBus() {
		return eventBus;
	}

	public MainView getMainView() {
		if (mainView == null) {
			mainView = new MainViewImpl();
		}
		return mainView;
	}

	public PlaceController getPlaceController() {
		return placeController;
	}

}
