package tk1.ue7;

import java.io.Serializable;

public class MandelRenderResponse implements Serializable {

	public int[] data;
	public long id;
	public int imgWidth;
	public int imgHeight;
	
	private static final long serialVersionUID = 7118767549047519017L;

	public MandelRenderResponse (long id, int[] data, int imgWidth, int imgHeight)
	{
		this.id = id;
		this.data = data;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
	}
}
