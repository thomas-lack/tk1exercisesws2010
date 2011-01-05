package tk1.ue9.server.webservice.utils;

import tk1.ue9.server.webservice.TemperatureUnit;

public class DegreeFahrenheit {

	public static double convert(double value, TemperatureUnit to) {
		if(to.value().equals(TemperatureUnit.KELVIN)){
			return (value + 459.67) * 5 / 9;
		}
		else if(to.value().equals(TemperatureUnit.DEGREE_RANKINE)){
			return value + 459.67;
		}
		else if(to.value().equals(TemperatureUnit.DEGREE_REAUMUR)){
			return (value - 32) * 4 / 9;
		}
		else if(to.value().equals(TemperatureUnit.DEGREE_CELSIUS)){
			return (value - 32) * 5 / 9;
		}
		
		return value;
	}
}
