package ru.abelitsky.diary.client;

import ru.abelitsky.diary.client.views.MainView;
import ru.abelitsky.diary.client.views.MainViewImpl;

public class ClientFactoryImpl implements ClientFactory {

	private MainView mainView;

	public MainView getMainView() {
		if (mainView == null) {
			mainView = new MainViewImpl();
		}
		return mainView;
	}

}
