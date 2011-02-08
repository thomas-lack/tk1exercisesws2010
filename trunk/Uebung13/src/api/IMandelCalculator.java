package api;

import org.mundo.annotation.mcRemote;

@mcRemote
public interface IMandelCalculator {

	void calculate(double x, double y);
}
