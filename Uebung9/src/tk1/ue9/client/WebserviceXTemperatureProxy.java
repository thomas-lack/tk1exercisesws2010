package tk1.ue9.client;

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
	public String[] getAvailableTemperatures() 
	{
	   return TemperatureUnit.getDesignators();
	}

	@Override
	public double convert(double value, String fromUnit, String toUnit) {
		TemperatureUnit from = TemperatureUnit.fromValue(fromUnit);
		TemperatureUnit to = TemperatureUnit.fromValue(toUnit);
		ConvertTemperature ct = new ConvertTemperature();
		return ct.getConvertTemperatureSoap().convertTemp(value, from, to);
	}

}
