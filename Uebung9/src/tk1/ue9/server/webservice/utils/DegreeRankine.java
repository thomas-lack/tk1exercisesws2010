package tk1.ue9.server.webservice.utils;

import tk1.ue9.server.webservice.TemperatureUnit;

public class DegreeRankine {
	public static double convert(double value, TemperatureUnit to) {
		if(to.value().equals(TemperatureUnit.KELVIN)){
			return value * 5 / 9;
		}
		else if(to.value().equals(TemperatureUnit.DEGREE_FAHRENHEIT)){
			return value - 459.67;
		}
		else if(to.value().equals(TemperatureUnit.DEGREE_REAUMUR)){
			return value * 4 / 9 - 218.52;
		}
		else if(to.value().equals(TemperatureUnit.DEGREE_CELSIUS)){
			return value * 5 / 9 - 273.15;
		}
		
		return value;
	}
}
