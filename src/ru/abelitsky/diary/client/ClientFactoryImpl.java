package ru.abelitsky.diary.client;

import com.google.gwt.core.shared.GWT;

import ru.abelitsky.diary.client.activities.MainActivity;
import ru.abelitsky.diary.client.services.MainService;
import ru.abelitsky.diary.client.services.MainServiceAsync;
import ru.abelitsky.diary.client.views.MainView;
import ru.abelitsky.diary.client.views.MainViewImpl;
import ru.abelitsky.diary.client.views.dialogs.JoinDaysDialog;
import ru.abelitsky.diary.client.views.dialogs.JoinDaysDialogImpl;

public class ClientFactoryImpl implements ClientFactory {

	private MainServiceAsync mainService;
	private MainView.Presenter mainActivity;
	private MainView mainView;
	private JoinDaysDialog joinDaysDialog;

	public JoinDaysDialog getJoinDaysDialog() {
		if (joinDaysDialog == null) {
			joinDaysDialog = new JoinDaysDialogImpl();
		}
		return joinDaysDialog;
	}

	public MainView.Presenter getMainActivity() {
		if (mainActivity == null) {
			mainActivity = new MainActivity(this);
		}
		return mainActivity;
	}

	public MainServiceAsync getMainService() {
		if (mainService == null) {
			mainService = GWT.create(MainService.class);
		}
		return mainService;
	}

	public MainView getMainView() {
		if (mainView == null) {
			mainView = new MainViewImpl(this);
		}
		return mainView;
	}

}
