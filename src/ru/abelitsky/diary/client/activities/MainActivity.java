package ru.abelitsky.diary.client.activities;

import ru.abelitsky.diary.client.ClientFactory;
import ru.abelitsky.diary.client.views.MainView;

public class MainActivity implements MainView.Presenter {

	private ClientFactory clientFactory;
	private MainView mainView;

	public MainActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.mainView = this.clientFactory.getMainView();
		this.mainView.setPresenter(this);
	}

	public MainView getView() {
		return clientFactory.getMainView();
	}

}
