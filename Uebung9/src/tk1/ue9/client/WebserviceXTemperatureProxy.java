package tk1.ue9.client;

import java.util.HashSet;
import java.util.Set;

import net.webservicex.ConvertTemperature;
import net.webservicex.TemperatureUnit;

/**
 * Proxy for the WebserviceX TempertureConverter webservice
 */
public class WebserviceXTemperatureProxy implements ITemperatureConverterProxy {

	@Override
	public String getName() {
		return "WebserviceX";
	}

	@Override
	public Set<String> getAvailableTempertures() {
		HashSet<String> units = new HashSet<String>();
		units.add(TemperatureUnit.DEGREE_CELSIUS.value());
		units.add(TemperatureUnit.DEGREE_FAHRENHEIT.value());
		units.add(TemperatureUnit.DEGREE_RANKINE.value());
		units.add(TemperatureUnit.DEGREE_REAUMUR.value());
		units.add(TemperatureUnit.KELVIN.value());
		
		return null;
	}

	@Override
	public double convert(double value, String fromUnit, String toUnit) {
		TemperatureUnit from = TemperatureUnit.fromValue(fromUnit);
		TemperatureUnit to = TemperatureUnit.fromValue(toUnit);
		ConvertTemperature ct = new ConvertTemperature();
		return ct.getConvertTemperatureSoap().convertTemp(value, from, to);
	}

}
