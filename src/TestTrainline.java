import java.io.IOException;
import java.util.*;
public class TestTrainline
{

	public static void main(String[] args) throws IOException
	{
		TrainSchedule schedule2 = ImportSchedule.importSchedule();
		schedule2.createTrains();//TO DO create trains should happen when creating graph or path
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 32);
		now = calendar.getTime();
		CreatePath path = new CreatePath(schedule2);
		path.getPath("HYM", "NCL", now).print();//Now taking String input to CreatePath
	}

}
