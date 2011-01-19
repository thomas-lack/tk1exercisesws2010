package tk1.ue9.server.webservice.utils;

import tk1.ue9.server.webservice.TemperatureUnit;

/**
 * Convert from Kelvin to other temperture units
 * 
 * used conversation table:
 * http://en.wikipedia.org/wiki/Temperature_conversion_formulas
 */

public class Kelvin {
	static public double convert(double value, TemperatureUnit to) {
		
		switch(to){
		case DEGREE_CELSIUS:
			return value - 273.15;
		case DEGREE_FAHRENHEIT:
			return value * 1.8 - 459.67;
		case DEGREE_RANKINE:
			return value * 1.8;
		case DEGREE_REAUMUR:
			return (value - 273.15) * 0.8;
		}
		
		return value;
	}
}
