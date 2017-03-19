import java.util.Calendar;
import java.util.Date;
/*
 * A class that provides useful methods to use when working with Date objects
 */
public class DateTime 
{
	/*
	 * Creates time in String format @ HH:MM
	 */
	public static String DateToString(Date d)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		String hour=""+c.get(Calendar.HOUR_OF_DAY);
		String min=""+c.get(Calendar.MINUTE);
		if(hour.length()==1)
		{
			hour=0+hour;
		}
		if(min.length()==1)
		{
			min=0+min;
		}
		return hour+":"+min;
	}
	public static Calendar DateToCalendar(Date d)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		return calendar;
	}
	/*
	 * Creates a date object with the specified hour and minutes of the current day
	 */
	public static Date setDate(int hour, int min)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND,0);
		Date date = calendar.getTime();
		return date;
	}
	//returns difference between two dates in minutes
	public static int dateDiff(Date later, Date earlier)
	{
		Calendar late = Calendar.getInstance();
		Calendar early = Calendar.getInstance();
		late.setTime(later);
		early.setTime(earlier);
		long difference = late.getTimeInMillis() - early.getTimeInMillis();
		int minutes = (int) difference/(1000*60);
		return minutes;
	}
}