package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import trainLineCreator.DateTime;

public class TestMinuteToHourMin {

	@Test
	public void test65minutes() {
		int minutes=65;
		assertEquals("1 hour and 5 minutes", DateTime.minutesToHourMin(minutes));
	}
	@Test
	public void test400minutes(){
		int minutes = 400;
		assertEquals("6 hours and 40 minutes", DateTime.minutesToHourMin(minutes));
	}

}
