package ru.abelitsky.diary.client;

import ru.abelitsky.diary.client.services.MainServiceAsync;
import ru.abelitsky.diary.client.views.MainView;
import ru.abelitsky.diary.client.views.dialogs.JoinDaysDialog;

public interface ClientFactory {

	public JoinDaysDialog getJoinDaysDialog();
	
	public MainView.Presenter getMainActivity();

	public MainServiceAsync getMainService();

	public MainView getMainView();

}
