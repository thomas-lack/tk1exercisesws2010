package tk1.ue9.server.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import tk1.ue9.server.webservice.utils.DegreeCelsius;
import tk1.ue9.server.webservice.utils.DegreeFahrenheit;
import tk1.ue9.server.webservice.utils.DegreeRankine;
import tk1.ue9.server.webservice.utils.DegreeReaumur;
import tk1.ue9.server.webservice.utils.Kelvin;

/**
 * Webservice to convert between termperature units
 */

@WebService(name = "LocalConvertTemperature")
@SOAPBinding(style = Style.RPC)
public class ConvertTemperatureWebservice {
	
	@WebMethod(operationName = "convert")
	@WebResult(name = "converted-value")
	public double convert(
			@WebParam(name = "value")double value, 
			@WebParam(name = "fromUnit")TemperatureUnit from, 
			@WebParam(name = "toUnit")TemperatureUnit to){
		
		System.out.println("value:" + value + " from: " + from + " to: " + to);
		
		switch(from){
		case DEGREE_CELSIUS:
			return DegreeCelsius.convert(value, to);
		case DEGREE_FAHRENHEIT:
			return DegreeFahrenheit.convert(value, to);
		case DEGREE_RANKINE:
			return DegreeRankine.convert(value, to);
		case DEGREE_REAUMUR:
			return DegreeReaumur.convert(value, to);
		case KELVIN:
			return Kelvin.convert(value, to);
		}
					
		return value;
	}
}
