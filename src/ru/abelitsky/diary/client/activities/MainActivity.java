package ru.abelitsky.diary.client.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import ru.abelitsky.diary.client.ClientFactory;
import ru.abelitsky.diary.client.views.MainView;

public class MainActivity extends AbstractActivity implements MainView.Presenter {

	private ClientFactory clientFactory;

	public MainActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		MainView mainView = this.clientFactory.getMainView();
		mainView.setPresenter(this);
		panel.setWidget(mainView);
	}

}
