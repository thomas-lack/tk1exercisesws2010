package api;

import org.mundo.annotation.mcRemote;

@mcRemote
public interface IMandelAgent {

	void run(double x_start, double y_start, double x_end, double y_end);
	void doCalculation();
}
