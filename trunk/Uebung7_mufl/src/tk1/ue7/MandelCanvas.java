package tk1.ue7;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;

import javax.swing.JComponent;

public class MandelCanvas extends JComponent {
	private int numElementsPerRow = 1;
	private Image image = null;
	private ColorModel colorModel;
	
	public MandelCanvas() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				MandelCanvas.this.resize(getWidth(), getHeight());
				super.componentResized(e);
			}
		});
		
		colorModel = generateColorModel();
	}
	
	public void setElementsPerRow(int elmentsPerRow){
		this.numElementsPerRow = elmentsPerRow;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Rectangle rect = g.getClipBounds();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, rect.width, rect.height);
		
		if(null != image)
			g.drawImage(image, 0, 0, null);
		
		super.paintComponent(g);
	}
	
	public void addSubimage(int id, int[] data, int width, int height){
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		
		MemoryImageSource memImage = new MemoryImageSource(
				width, height, colorModel, data, 0, width);
		
		int xOffset = (id % numElementsPerRow) * (getWidth() / numElementsPerRow);
		int yOffset = (id / numElementsPerRow) * (getHeight() / numElementsPerRow);
		
		g2.drawImage(
				getToolkit().createImage(memImage), 
				xOffset, 
				yOffset, 
				null);
	}
	
	public void resize(int width, int height){
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
		int iter;
		for (int i = 0; i < 255; i++)
		{
			r[i] = (byte)((i * 26) % 250);
			g[i] = (byte)((i *  2) % 250);
			b[i] = (byte)((i * 35) % 250);
		}
		return new IndexColorModel(8,255,r,g,b);
	}
}
