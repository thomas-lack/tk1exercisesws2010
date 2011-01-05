package tk1.ue9.server.webservice.utils;

import tk1.ue9.server.webservice.TemperatureUnit;

public class Kelvin {
	static public double convert(double value, TemperatureUnit to) {
		if(to.value().equals(TemperatureUnit.DEGREE_FAHRENHEIT)){
			return value * 1.8 - 459.67;
		}
		else if(to.value().equals(TemperatureUnit.DEGREE_RANKINE)){
			return value * 1.8;
		}
		else if(to.value().equals(TemperatureUnit.DEGREE_REAUMUR)){
			return (value - 273.15) * 0.8;
		}
		else if(to.value().equals(TemperatureUnit.DEGREE_CELSIUS)){
			return value - 273.15;
		}
		
		return value;
	}
}
