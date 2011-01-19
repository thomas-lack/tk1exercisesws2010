package tk1.ue9.server.webservice.utils;

import tk1.ue9.server.webservice.TemperatureUnit;

public class DegreeRankine {
	public static double convert(double value, TemperatureUnit to) {
		
		switch(to){
		case DEGREE_CELSIUS:
			return value * 5 / 9 - 273.15;
		case DEGREE_FAHRENHEIT:
			return value - 459.67;
		case DEGREE_REAUMUR:
			return value * 4 / 9 - 218.52;
		case KELVIN:
			return value * 5 / 9;
		}
		
		return value;
	}
}
