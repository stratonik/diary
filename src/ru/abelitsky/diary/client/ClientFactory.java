package ru.abelitsky.diary.client;

import ru.abelitsky.diary.client.views.MainView;

public interface ClientFactory {

	public MainView.Presenter getMainActivity();
	public MainView getMainView();

}
