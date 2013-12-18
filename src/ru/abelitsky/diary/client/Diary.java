package ru.abelitsky.diary.client;

import ru.abelitsky.diary.client.activities.MainActivity;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class Diary implements EntryPoint {

	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	public void onModuleLoad() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		MainActivity mainActivity = new MainActivity(clientFactory);
		RootPanel.get("appContainer").add(mainActivity.getView());
		RootPanel.get("loading").setVisible(false);
	}

}
