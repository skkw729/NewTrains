import java.io.IOException;
import java.util.*;
public class TestTrainline
{

	public static void main(String[] args) throws IOException
	{
		//System.out.println("import starting");
		TrainSchedule schedule2 = ImportSchedule.importSchedule();//importing schedule takes the most time
		//System.out.println("import finished");
		
		//Date objects to test with
		Date now = new Date();
		Date when = DateTime.setDate(30, 12, 1990, 13, 15);//day, month, year, hour, minute
		
		//test path1
		CreatePath path = new CreatePath(schedule2, when);//date can now be specified to create more trains at this date
		path.getPath("GLC", "NCL", when).print();//Now taking String input to CreatePath
		
		//test path2
		path = new CreatePath(schedule2, now);//path creation requires 2 lines of code
		path.getPath("GLC", "NCL", now).print();
	}

}