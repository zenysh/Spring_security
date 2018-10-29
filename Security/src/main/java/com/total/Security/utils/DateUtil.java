package com.total.Security.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public static Date getTokenExpireDate(Date current) {

		Calendar c = Calendar.getInstance();
		c.setTime(current);

		c.add(Calendar.YEAR, 0);
		c.add(Calendar.MONTH, 0);
		c.add(Calendar.DATE, 0);
		c.add(Calendar.HOUR, 0);
		c.add(Calendar.MINUTE, 2);
		c.add(Calendar.SECOND, 0);

		Date currentDatePlusOne = c.getTime();
		System.out.println(dateFormat.format(currentDatePlusOne));
		return currentDatePlusOne;

	}

	public static boolean compareDate(Date createdDate, Date expireDate) {

		if (expireDate.compareTo(createdDate) < 0) {
			return false;

		}
		if (expireDate.compareTo(createdDate) == 0) {

			return true;
		}
		if (expireDate.compareTo(createdDate) > 0) {
			return true;
		}
		return false;
	}
	
	public static Date currentDateTimePlusMinutes(int minutes) {
		Date currentDate = new Date();
		LocalDateTime localDateTime = currentDate.toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDateTime();
		localDateTime = localDateTime.plusMinutes(minutes);
		Date currentDatePlusMinutes = Date
				.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

		return currentDatePlusMinutes;
	}
	
	public static boolean isCurrentTimeBeforeThanGivenTime(Date when) {
		Date now = new Date();
		if (now.before(when)) {
			return true;
		}
		return false;
	}


}
