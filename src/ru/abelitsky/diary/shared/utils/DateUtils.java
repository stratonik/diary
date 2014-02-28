package ru.abelitsky.diary.shared.utils;

import java.util.Date;

import com.google.gwt.i18n.shared.DateTimeFormat;

public final class DateUtils {

	private DateUtils() {
	}
	
	public static int compare(Date date1, Date date2) {
		if (date1 == null) {
			return (date2 == null)? 0 : +1;
		} else {
			if (date2 == null) {
				return -1;
			} else {
				DateTimeFormat format = DateTimeFormat.getFormat("yyyyMMdd");
				String date1Str = format.format(date1);
				String date2Str = format.format(date2);
				return date1Str.compareTo(date2Str);
			}
		}
	}

	public static boolean isBefore(Date date1, Date date2) {
		return compare(date1, date2) < 0;
	}

	public static boolean isEquals(Date date1, Date date2) {
		return compare(date1, date2) == 0;
	}

}
