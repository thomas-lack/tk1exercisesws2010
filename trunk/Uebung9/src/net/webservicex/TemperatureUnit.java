
package net.webservicex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TemperatureUnit.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TemperatureUnit">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="degreeCelsius"/>
 *     &lt;enumeration value="degreeFahrenheit"/>
 *     &lt;enumeration value="degreeRankine"/>
 *     &lt;enumeration value="degreeReaumur"/>
 *     &lt;enumeration value="kelvin"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
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
    
    TemperatureUnit(String v, String d) 
    {
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
