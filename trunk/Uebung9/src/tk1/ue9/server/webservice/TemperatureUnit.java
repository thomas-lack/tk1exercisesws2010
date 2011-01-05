package tk1.ue9.server.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TemperatureUnit")
@XmlEnum
public enum TemperatureUnit {

    @XmlEnumValue("degreeCelsius")
    DEGREE_CELSIUS("degreeCelsius"),
    @XmlEnumValue("degreeFahrenheit")
    DEGREE_FAHRENHEIT("degreeFahrenheit"),
    @XmlEnumValue("degreeRankine")
    DEGREE_RANKINE("degreeRankine"),
    @XmlEnumValue("degreeReaumur")
    DEGREE_REAUMUR("degreeReaumur"),
    @XmlEnumValue("kelvin")
    KELVIN("kelvin");
    private final String value;

    TemperatureUnit(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TemperatureUnit fromValue(String v) {
        for (TemperatureUnit c: TemperatureUnit.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
