package tk1.ue9.client;

/*
 * Proxy-Interface for temparture converter services
 */
public interface ITemperatureConverterProxy {
	/**
	 * Returns the name of the service
	 * @return
	 */
	public String getName();
	
	/**
	 * Returns a set of available temperature units
	 * 
	 * @return
	 */
	public String[] getAvailableTemperatures();
	
	/**
	 * Convert <i>value</i> from <i>fromValue</i> to <i>toValue</i>
	 * 
	 * @param value
	 * @param fromUnit
	 * @param toUnit
	 * @return
	 */
	public double convert(double value, String fromUnit, String toUnit);
}
