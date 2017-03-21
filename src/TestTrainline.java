import java.util.*;
public class TestTrainline
{

	public static void main(String[] args)
	{
		//create stations
		Stations YRK = new Stations("York", "YRK");
		Stations NTR = new Stations("Northallerton", "NTR");
		Stations DAR = new Stations("Darlington", "DAR");
		Stations DHM = new Stations("Durham", "DHM");
		Stations NCL = new Stations("Newcastle", "NCL");
		Stations SUN = new Stations("Sunderland", "SUN");
		Stations MPT = new Stations("Morpeth", "MPT");
		Stations ALM = new Stations("Alnmouth", "ALM");
		Stations BWK = new Stations("Berwick-upon-Tweed", "BWK");
		Stations DUN = new Stations("Dunbar", "DUN");
		Stations EDB = new Stations("Edinburgh Waverley", "EDB");
		Stations HYM = new Stations("Haymarket", "HYM");
		Stations MTH = new Stations("Motherwell", "MTH");
		Stations GLC = new Stations("Glasgow Central", "GLC");
		Stations INK = new Stations("Inverkeithing", "INK");
		Stations KDY = new Stations("Kirkcaldy", "KDY");
		Stations LEU = new Stations("Leuchars","LEU");
		Stations DEE = new Stations("Dundee", "DEE");
		Stations ARB = new Stations("Arbroath", "ARB");
		Stations MTS = new Stations("Montrose","MTS");
		Stations STN = new Stations("Stonehaven","STN");
		Stations ABD = new Stations("Aberdeen", "ABD");
		Stations FKG = new Stations("Falkirk Grahamston", "FKG");
		Stations STG = new Stations("Stirling", "STG");
		Stations GLE = new Stations("Gleneagles", "GLE");
		Stations PTH = new Stations("Perth", "PTH");
		Stations PIT = new Stations("Pitlochry", "PIT");
		Stations KIN = new Stations("Kingussie","KIN");
		Stations AVM = new Stations("Aviemore", "AVM");
		Stations INV = new Stations("Inverness", "INV");
		
		//Adding connections
		YRK.addConnection(NTR, 20);
		YRK.addConnection(DAR, 26);
		
		NTR.addConnection(DAR, 11);
		
		DAR.addConnection(DHM, 15);
		DAR.addConnection(NCL, 30);
		
		DHM.addConnection(NCL, 15);
		
		NCL.addConnection(SUN, 30);
		NCL.addConnection(MPT, 22);
		NCL.addConnection(BWK, 44);
		
		MPT.addConnection(ALM, 14);
		
		ALM.addConnection(EDB, 65);
		ALM.addConnection(BWK, 20);
		
		BWK.addConnection(DUN, 22);
		
		DUN.addConnection(EDB, 24);
		
		EDB.addConnection(HYM, 5);
		
		HYM.addConnection(INK, 16);
		HYM.addConnection(MTH, 35);
		HYM.addConnection(FKG, 29);
		
		MTH.addConnection(GLC, 22);
		
		INK.addConnection(KDY, 15);
		
		KDY.addConnection(LEU, 23);
		
		LEU.addConnection(DEE, 16);
		
		DEE.addConnection(ARB, 16);
		
		ARB.addConnection(MTS, 15);
		
		MTS.addConnection(STN, 22);
		
		STN.addConnection(ABD, 20);
		
		FKG.addConnection(STG, 17);
		
		STG.addConnection(GLE, 23);
		
		GLE.addConnection(PTH, 16);
		
		PTH.addConnection(PIT, 30);
		
		PIT.addConnection(KIN, 46);
		
		KIN.addConnection(AVM, 11);
		
		AVM.addConnection(INV, 35);
		
		//add trainlines
		Trainline lineA = new Trainline("Line A");
		Trainline lineB = new Trainline("Line B");
		Trainline lineC = new Trainline("Line C");
		Trainline lineD = new Trainline("Line D");
		
		//add stations to trainlines
		lineA.addStation(YRK);
		lineA.addStation(DAR);
		lineA.addStation(NCL);
		lineA.addStation(BWK);
		lineA.addStation(DUN);
		lineA.addStation(EDB);
		lineA.addStation(HYM);
		lineA.addStation(INK);
		lineA.addStation(KDY);
		lineA.addStation(LEU);
		lineA.addStation(DEE);
		lineA.addStation(ARB);
		lineA.addStation(MTS);
		lineA.addStation(STN);
		lineA.addStation(ABD);
		
		lineB.addStation(YRK);
		lineB.addStation(NTR);
		lineB.addStation(DAR);
		lineB.addStation(DHM);
		lineB.addStation(NCL);
		lineB.addStation(SUN);
		
		lineC.addStation(YRK);
		lineC.addStation(NTR);
		lineC.addStation(DAR);
		lineC.addStation(DHM);
		lineC.addStation(NCL);
		lineC.addStation(MPT);
		lineC.addStation(ALM);
		lineC.addStation(BWK);
		lineC.addStation(DUN);
		lineC.addStation(EDB);
		lineC.addStation(HYM);
		lineC.addStation(MTH);
		lineC.addStation(GLC);
		
		lineD.addStation(YRK);
		lineD.addStation(DAR);
		lineD.addStation(NCL);
		lineD.addStation(BWK);
		lineD.addStation(DUN);
		lineD.addStation(EDB);
		lineD.addStation(HYM);
		lineD.addStation(FKG);
		lineD.addStation(STG);
		lineD.addStation(GLE);
		lineD.addStation(PTH);
		lineD.addStation(PIT);
		lineD.addStation(KIN);
		lineD.addStation(AVM);
		lineD.addStation(INV);
		
		//initialise trainlines with fixed start/end stations
		lineA.completeTrainline();
		lineB.completeTrainline();
		lineC.completeTrainline();
		lineD.completeTrainline();
		//TO DO create a class to import trainlines to remove the need for completeTrainline()
		
		//Create a schedule
		TrainSchedule schedule = new TrainSchedule();
		
		//add trainlines to schedule
		schedule.addTrainline(lineA);
		schedule.addTrainline(lineB);
		schedule.addTrainline(lineC);
		schedule.addTrainline(lineD);
		//TO DO constructor of TrainSchedule(Date now) to generate schedule at specified date
		//remove the need to addTrainline() by automating it in the constructor
		
		//create some trains
		schedule.createTrains();
		//TO DO create a class to import connection times and store reverse
		//and remove it from createTrains() - better coupling
		
		//test code here
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 32);
		now = calendar.getTime();
		CreatePath path = new CreatePath(schedule);
		path.getPath(YRK, SUN, now).print();
	}

}
