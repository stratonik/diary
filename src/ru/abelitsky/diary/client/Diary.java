package ru.abelitsky.diary.client;

import ru.abelitsky.diary.client.views.MainView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class Diary implements EntryPoint {

	public void onModuleLoad() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		
		MainView.Presenter mainActivity = clientFactory.getMainActivity();
		MainView mainView = clientFactory.getMainView();
		mainView.setPresenter(mainActivity);
		RootLayoutPanel.get().add(mainView);

		RootPanel.get("loading").setVisible(false);
	}

}
