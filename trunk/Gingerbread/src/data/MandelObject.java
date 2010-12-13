package data;

import java.io.Serializable;

public class MandelObject implements Serializable {

	private static final long serialVersionUID = -5779611856898835335L;
	
	private int index;
	/**
	private double xstart;
	private double xend;
	private double ystart;
	private double yend;
	**/
	private double x;
	private double y;
	private int result;
	
	
	public MandelObject(int index, double x, double y)
	{
		this.index = index;
		/** 
		this.xstart = xstart;
		this.xend = xend;
		this.ystart = ystart;
		this.yend = yend;
		**/
		this.x = x;
		this.y = y;
		
		this.result = 0;
	}
	
	
	/**
	 *  SET functions
	 **/

	void set_index(int index)
	{
		this.index = index;
	}
	/**
	void set_xstart(double xstart)
	{
		this.xstart = xstart;
	}
	
	void set_ystart(double ystart)
	{
		this.ystart = ystart;
	}
	
	void set_xend(double xend)
	{
		this.xend = xend;
	}
	
	void set_yend(double yend)
	{
		this.yend = yend;
	}
	**/
	
	void set_x(double x)
	{
		this.x = x;
	}
	
	void set_y(double y)
	{
		this.y = y;
	}
	
	public void set_result(int result)
	{
		this.result = result;
	}
	
	/**
	 * GET functions 
	 **/
	
	public int get_indes()
	{
		return this.index;
	}
	/**
	public double get_xstart()
	{
		return this.xstart;
	}
	
	public double get_ystart()
	{
		return this.ystart;
	}

	
	public double get_xend()
	{
		return this.xend;
	}
	
	public double get_yend()
	{
		return this.yend;
	}
	**/
	
	public double get_x()
	{
		return this.x;
	}
	
	public double get_y()
	{
		return this.y;
	}
	public int get_result()
	{
		return this.result;
	}
}
