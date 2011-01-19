package tk1.ue9.server.webservice.utils;

import tk1.ue9.server.webservice.TemperatureUnit;


/**
 * Class to convert from degree celsius to other temerature units
 */
public class DegreeCelsius{
	
	static public double convert(double value, TemperatureUnit to) {
		
		switch(to){
		case DEGREE_FAHRENHEIT:
			return value * 1.8 + 32;
		case DEGREE_RANKINE:
			return value * 1.8 + 491.67;
		case DEGREE_REAUMUR:
			return value * 0.8;
		case KELVIN:
			return value + 273.15;
		}
		
		return value;
	}
}
