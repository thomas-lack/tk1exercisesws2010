package tk1.ue9.client;

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
	public String[] getAvailableTemperatures() 
	{
	   return TemperatureUnit.getDesignators();
	}

	@Override
	public double convert(double value, String fromUnit, String toUnit) {
		TemperatureUnit from = TemperatureUnit.fromValue(fromUnit);
		TemperatureUnit to = TemperatureUnit.fromValue(toUnit);
		ConvertTemperatureWebserviceService lcts = new ConvertTemperatureWebserviceService();
		return lcts.getLocalConvertTemperaturePort().convert(value, from, to);
	}

}
