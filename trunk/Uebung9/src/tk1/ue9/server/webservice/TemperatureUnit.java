package tk1.ue9.server.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TemperatureUnit")
@XmlEnum
public enum TemperatureUnit {

    @XmlEnumValue("degreeCelsius")
    DEGREE_CELSIUS("degreeCelsius", "Celsius"),
    @XmlEnumValue("degreeFahrenheit")
    DEGREE_FAHRENHEIT("degreeFahrenheit", "Fahrenheit"),
    @XmlEnumValue("degreeRankine")
    DEGREE_RANKINE("degreeRankine", "Rankine"),
    @XmlEnumValue("degreeReaumur")
    DEGREE_REAUMUR("degreeReaumur", "Reaumur"),
    @XmlEnumValue("kelvin")
    KELVIN("kelvin", "Kelvin");
    private final String value;
    private final String designator;
    
    TemperatureUnit(String v, String d) {
        value = v;
        designator = d;
    }

    public String value() {
        return value;
    }

    public static TemperatureUnit fromValue(String v) {
        for (TemperatureUnit c: TemperatureUnit.values()) {
            if (c.designator.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
    public static String[] getDesignators()
    {
       String[] ret = new String[TemperatureUnit.values().length];
       int i = 0;
       for (TemperatureUnit c: TemperatureUnit.values())
       {
          ret[i] = c.designator;
          i++;
       }
       return ret;
    }
}
