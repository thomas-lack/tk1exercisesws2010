package tk1.ue7;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;

import javax.swing.JComponent;

public class MandelCanvas extends JComponent
{
	private static final long serialVersionUID = 3458391705994850395L;
   private Image image = null;
	private ColorModel colorModel;
	
	public MandelCanvas() 
	{
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				MandelCanvas.this.resize(getWidth(), getHeight());
				super.componentResized(e);
			}
		});
		
		colorModel = generateColorModel();
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		Rectangle rect = g.getClipBounds();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, rect.width, rect.height);
		
		if(null != image)
			g.drawImage(image, 0, 0, null);
		
		super.paintComponent(g);
	}
	
	//public void addSubimage(int id, int[] data, int width, int height)
	public void addSubimage(int[] data, int x, int y, int width, int height)
	{
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		
		MemoryImageSource memImage = new MemoryImageSource(
				width, height, colorModel, data, 0, width);
				
		g2.drawImage(
				getToolkit().createImage(memImage), 
				x, 
				y, 
				null);
		this.repaint();
	}
	
	public void resize(int width, int height)
	{
		image = createImage(width, height);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, width, height);
	}
	
	private IndexColorModel generateColorModel()
	{
		byte[] r = new byte[255];
		byte[] g = new byte[255];
		byte[] b = new byte[255];
		
		for (int i = 0; i < 255; i++)
		{
			r[i] = (byte)((i * 26) % 250);
			g[i] = (byte)((i *  2) % 250);
			b[i] = (byte)((i * 35) % 250);
		}
		return new IndexColorModel(8,255,r,g,b);
	}
}
