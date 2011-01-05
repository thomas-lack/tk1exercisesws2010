package tk1.ue9.server.webservice.utils;

import tk1.ue9.server.webservice.TemperatureUnit;

public class DegreeReaumur {
	public static double convert(double value, TemperatureUnit to) {
		if(to.value().equals(TemperatureUnit.KELVIN)){
			return value * 1.25 + 273.15;
		}
		else if(to.value().equals(TemperatureUnit.DEGREE_FAHRENHEIT)){
			return value * 2.25 + 32;
		}
		else if(to.value().equals(TemperatureUnit.DEGREE_RANKINE)){
			return value * 2.25 + 491.67;
		}
		else if(to.value().equals(TemperatureUnit.DEGREE_CELSIUS)){
			return value * 1.25;
		}
		
		return value;
	}
}
