package tk1.ue9.server.webservice.utils;

import tk1.ue9.server.webservice.TemperatureUnit;

public class DegreeFahrenheit {

	public static double convert(double value, TemperatureUnit to) {
		
		switch(to){
		case DEGREE_CELSIUS:
			return (value - 32) * (5 / 9);
		case DEGREE_RANKINE:
			return value + 459.67;
		case DEGREE_REAUMUR:
			return (value - 32) * (4 / 9);
		case KELVIN:
			return (value + 459.67) * (5 / 9);
		}
		
		return value;
	}
}
