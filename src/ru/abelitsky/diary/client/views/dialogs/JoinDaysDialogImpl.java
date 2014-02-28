package ru.abelitsky.diary.client.views.dialogs;

import java.util.Date;

import ru.abelitsky.diary.shared.utils.DateUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class JoinDaysDialogImpl extends DialogBox implements JoinDaysDialog {

	interface JoinDaysDialogImplUiBinder extends UiBinder<Widget, JoinDaysDialogImpl> {
	}

	private static JoinDaysDialogImplUiBinder uiBinder = GWT.create(JoinDaysDialogImplUiBinder.class);

	private Presenter presenter;
	private Date currentDate;

	@UiField
	RadioButton beginDateChoice;
	@UiField
	DateBox beginDate;
	@UiField
	RadioButton endDateChoice;
	@UiField
	DateBox endDate;

	public JoinDaysDialogImpl() {
		super();
		setText("Объединение дней");
		setWidget(uiBinder.createAndBindUi(this));

		DateBox.Format format = new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd.MM.yyyy"));
		beginDate.setFormat(format);
		endDate.setFormat(format);
	}

	@UiHandler("beginDateChoice")
	void onBeginDateChoiceValueChange(ValueChangeEvent<Boolean> event) {
		beginDate.setEnabled(true);
		endDate.setEnabled(false);
		endDate.setValue(currentDate);
	}

	@UiHandler("okButton")
	void onOkButtonClick(ClickEvent event) {
		Date begin = beginDate.getValue();
		Date end = endDate.getValue();
		if ((begin != null) && (end != null) && DateUtils.isBefore(begin, end)) {
			super.hide();
			if (presenter != null) {
				presenter.join(begin, end);
			}
		}
	}

	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event) {
		super.hide();
	}

	@UiHandler("endDateChoice")
	void onEndDateChoiceValueChange(ValueChangeEvent<Boolean> event) {
		beginDate.setEnabled(false);
		beginDate.setValue(currentDate);
		endDate.setEnabled(true);
	}

	@Override
	public void setCurrentDate(Date date) {
		this.currentDate = date;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void showDialog() {
		beginDateChoice.setValue(true, true);
		beginDate.setValue(currentDate);
		endDate.setValue(currentDate);
		super.center();
	}

}
