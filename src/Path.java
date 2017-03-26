/*
 * Print statements required by web interface
 */

public interface Path
{
	void print();//prints all other methods to console
	String getRoute();//the route taken
	String getRouteTimes();//the departure time and the arrival times at each station on route
	String getTransfers();//the number of transfers and the stations to transfer at
}
