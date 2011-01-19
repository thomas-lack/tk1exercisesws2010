package tk1.ue9.server.webservice.utils;

import tk1.ue9.server.webservice.TemperatureUnit;

public class DegreeReaumur {
	public static double convert(double value, TemperatureUnit to) {
		switch(to){
		case DEGREE_CELSIUS:
			return value * 1.25;
		case DEGREE_FAHRENHEIT:
			return value * 2.25 + 32;
		case DEGREE_RANKINE:
			return value * 2.25 + 491.67;
		case KELVIN:
			return value * 1.25 + 273.15;
		}
				
		return value;
	}
}
