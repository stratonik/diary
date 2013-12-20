package ru.abelitsky.diary.client.views;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class MainViewImpl extends Composite implements MainView {

	private static MainViewImplUiBinder uiBinder = GWT.create(MainViewImplUiBinder.class);

	interface MainViewImplUiBinder extends UiBinder<Widget, MainViewImpl> {
	}

	private Presenter presenter;
	
	@UiField
	DatePicker calendar;
	@UiField
	Label currentDate;

	public MainViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("calendar")
	void onValueChange(ValueChangeEvent<Date> event) {
		currentDate.setText(event.getValue().toString());
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
