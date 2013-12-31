package ru.abelitsky.diary.server.services;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.abelitsky.diary.client.services.MainService;
import ru.abelitsky.diary.shared.model.DiaryRecordDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MainServiceImpl extends RemoteServiceServlet implements MainService {

	private static final long serialVersionUID = 1L;

	private static final String[] WEEKDAY_NAMES = { "", "воскресенье", "понедельник", "вторник", "среда", "четверг",
			"пятница", "суббота" };

	private static final String[] MONTH_NAMES = { "января", "февраля", "марта", "апреля", "мая", "июня", "июля",
			"августа", "сентября", "октября", "ноября", "декабря" };

	private static final String testLong = "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet."
			+ "A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to PopupPanel.setWidth(String) and PopupPanel.setHeight(String) will set the width and height of the dialog box itself, even if a widget has not been added as yet.";

	@Override
	public DiaryRecordDTO getRecord(Date date) {
		DiaryRecordDTO result = new DiaryRecordDTO();
		result.setDate(date);

		DateFormatSymbols symbols = DateFormatSymbols.getInstance(new Locale("ru"));
		symbols.setMonths(MONTH_NAMES);
		symbols.setWeekdays(WEEKDAY_NAMES);
		SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy, EEEE", symbols);
		result.setDateString(format.format(date));

		if (date.getDay() == 0 || date.getDay() == 6) {
			result.setSource(testLong);
			result.setHtml("<p>" + testLong + "</p>");
		} else {
			result.setSource("Test!!!");
			result.setHtml("<p>Test!!!</p>");
		}

		return result;
	}

}
