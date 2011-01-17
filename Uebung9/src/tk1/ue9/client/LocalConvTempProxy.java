package tk1.ue9.client;

import java.util.HashSet;
import java.util.Set;

import de.localconvtemp.ConvertTemperatureWebserviceService;
import de.localconvtemp.TemperatureUnit;

/**
 * Proxy for the local temperature converter
 */
public class LocalConvTempProxy implements ITemperatureConverterProxy {

	@Override
	public String getName() {
		return "Local";
	}

	@Override
	public Set<String> getAvailableTempertures() {
		HashSet<String> units = new HashSet<String>();
		units.add(TemperatureUnit.DEGREE_CELSIUS.value());
		units.add(TemperatureUnit.DEGREE_FAHRENHEIT.value());
		units.add(TemperatureUnit.DEGREE_RANKINE.value());
		units.add(TemperatureUnit.DEGREE_REAUMUR.value());
		units.add(TemperatureUnit.KELVIN.value());
		
		return units;
	}

	@Override
	public double convert(double value, String fromUnit, String toUnit) {
		TemperatureUnit from = TemperatureUnit.fromValue(fromUnit);
		TemperatureUnit to = TemperatureUnit.fromValue(toUnit);
		ConvertTemperatureWebserviceService lcts = new ConvertTemperatureWebserviceService();
		return lcts.getLocalConvertTemperaturePort().convert(value, from, to);
	}

}
