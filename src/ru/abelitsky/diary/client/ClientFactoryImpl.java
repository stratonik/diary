package ru.abelitsky.diary.client;

import ru.abelitsky.diary.client.activities.MainActivity;
import ru.abelitsky.diary.client.views.MainView;
import ru.abelitsky.diary.client.views.MainViewImpl;

public class ClientFactoryImpl implements ClientFactory {

	private MainView.Presenter mainActivity;
	private MainView mainView;

	public MainView.Presenter getMainActivity() {
		if (mainActivity == null) {
			mainActivity = new MainActivity(this);
		}
		return mainActivity;
	}

	public MainView getMainView() {
		if (mainView == null) {
			mainView = new MainViewImpl();
		}
		return mainView;
	}

}
