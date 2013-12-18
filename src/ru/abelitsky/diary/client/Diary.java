package ru.abelitsky.diary.client;

import ru.abelitsky.diary.client.activities.AppActivityMapper;
import ru.abelitsky.diary.client.places.AppPlaceHistoryMapper;
import ru.abelitsky.diary.client.places.MainPlace;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class Diary implements EntryPoint {

	public void onModuleLoad() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);

		PlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(clientFactory.getPlaceController(), clientFactory.getEventBus(), new MainPlace());

		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, clientFactory.getEventBus());

		SimplePanel appContainer = new SimplePanel();
		activityManager.setDisplay(appContainer);

		RootPanel.get("appContainer").add(appContainer);
		historyHandler.handleCurrentHistory();

		RootPanel.get("loading").setVisible(false);
	}

}
