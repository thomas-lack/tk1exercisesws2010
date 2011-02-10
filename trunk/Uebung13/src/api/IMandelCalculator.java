package api;

import org.mundo.annotation.mcRemote;

@mcRemote
public interface IMandelCalculator {

	void run(double x, double y);
	void doCalculation();
}
