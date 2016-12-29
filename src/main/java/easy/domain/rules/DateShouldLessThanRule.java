package easy.domain.rules;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class DateShouldLessThanRule<T> extends BaseRule<T, Date> {

	private Date date;

	public DateShouldLessThanRule(String property, Date date) {
		super(property);

		this.date = DateUtils.truncate(date, Calendar.SECOND);
	}

	@Override
	public boolean isSatisfy(T model) {
		Date d = this.getObjectAttrValue(model);
		d = DateUtils.truncate(d, Calendar.SECOND);

		return d.compareTo(this.date) < 0;
	}

}
