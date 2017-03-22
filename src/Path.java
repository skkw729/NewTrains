import java.util.Date;
import java.util.List;
import java.util.Map;

public interface Path
{
	Map<Stations, Train> getTrainMap();
	Train getTrain(Stations s);
	void putTrain(Stations s, Train t);
	Date getTransferTime(Stations s);
	void putTransferStation(Stations s, Date time);
	void setStations(List<Stations> list);
	List<Stations> getStations();
	void print();
	String getRoute();
	String getRouteTimes();
}
