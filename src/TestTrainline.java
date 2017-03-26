import java.io.IOException;
import java.util.*;
public class TestTrainline
{

	public static void main(String[] args) throws IOException
	{
		TrainSchedule schedule2 = ImportSchedule.importSchedule();
		schedule2.createTrains();//creates default set of train for today and tomorrow
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 32);
		calendar.set(Calendar.DATE,21);
		calendar.set(Calendar.MONTH, 0);
		Date now = calendar.getTime();
		CreatePath path = new CreatePath(schedule2, now);//date can now be specified to create more trains at this date
		path.getPath("GLC", "NCL", now).print();//Now taking String input to CreatePath
	}

}