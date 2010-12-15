package tk1.ue7;

/**
 * A structure that holds the results 
 * of a Mandelbaum calculation
 * A object of this type will be returned to the
 * Master ( MandelClient) 
 */

import java.io.Serializable;

public class MandelRenderResponse implements Serializable 
{
	public int[] data;
	public int id;
	public int imgWidth;
	public int imgHeight;
	
	private static final long serialVersionUID = 7118767549047519017L;

	public MandelRenderResponse (int id, int[] data, int imgWidth, int imgHeight)
	{
		this.id = id;
		this.data = data;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
	}
}
