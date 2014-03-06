package ru.abelitsky.diary.server.services;

import java.io.StringWriter;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder;
import org.eclipse.mylyn.wikitext.textile.core.TextileLanguage;

import ru.abelitsky.diary.client.services.MainService;
import ru.abelitsky.diary.shared.model.DiaryRecordDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MainServiceImpl extends RemoteServiceServlet implements MainService {

	private static final long serialVersionUID = 1L;

	private static final String[] WEEKDAY_NAMES = { "", "воскресенье", "понедельник", "вторник", "среда", "четверг",
			"пятница", "суббота" };

	private static final String[] MONTH_NAMES = { "января", "февраля", "марта", "апреля", "мая", "июня", "июля",
			"августа", "сентября", "октября", "ноября", "декабря" };

	private static final String TAG_PATTERN = "^@tag\\s(.*?)$";

	private static Map<String, String> diary = new HashMap<String, String>();

	private String convertToHtml(String source) {
		Pattern tagPattern = Pattern.compile(TAG_PATTERN, Pattern.MULTILINE);
		Matcher tagMatcher = tagPattern.matcher(source);
		StringBuffer sb = new StringBuffer();
		while (tagMatcher.find()) {
			tagMatcher.appendReplacement(sb, "\np{font-size:smaller}. _" + formatTags(tagMatcher.group(1)) + "_");
		}
		tagMatcher.appendTail(sb);
		source = sb.toString();

		StringWriter writer = new StringWriter();
		HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
		builder.setEmitAsDocument(false);

		MarkupParser markupParser = new MarkupParser();
		markupParser.setMarkupLanguage(new TextileLanguage());
		markupParser.setBuilder(builder);
		markupParser.parse(source);

		return writer.toString();
	}

	private String formatTags(String tagGroup) {
		if (tagGroup != null) {
			StringBuilder sb = new StringBuilder();
			for (String tag : tagGroup.split(",")) {
				tag = tag.trim();
				if ((tag != null) && !tag.isEmpty()) {
					if (sb.length() > 0) {
						sb.append(", ");
					}
					sb.append("#").append(tag);
				}
			}
			return sb.toString();
		} else {
			return "";
		}
	}

	@Override
	public DiaryRecordDTO get(Date date) {
		DiaryRecordDTO result = new DiaryRecordDTO();
		result.setDate(date);

		DateFormatSymbols symbols = DateFormatSymbols.getInstance(new Locale("ru"));
		symbols.setMonths(MONTH_NAMES);
		symbols.setWeekdays(WEEKDAY_NAMES);
		SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy, EEEE", symbols);
		result.setDateString(format.format(date));

		String key = new SimpleDateFormat("yyyy-MM-dd").format(date);
		String text = diary.get(key);
		if (text == null) {
			text = "";
		}
		result.setSource(text);
		result.setHtml(convertToHtml(text));

		return result;
	}

	@Override
	public DiaryRecordDTO join(Date fromDate, Date toDate, Date currentDate) {
		return get(currentDate);
	}

	@Override
	public DiaryRecordDTO save(Date date, String record) {
		System.out.println("Save in " + new Date());

		String key = new SimpleDateFormat("yyyy-MM-dd").format(date);
		diary.put(key, record);
		
		return get(date);
	}

}
