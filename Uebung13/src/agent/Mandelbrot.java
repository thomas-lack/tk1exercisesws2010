package agent;

class Mandelbrot{
	
	private int width, height; 
	private int data[];
	private double xstart; 
	private double xend;
	private double ystart; 
	private double yend;
	private int maxiter;
	
	public Mandelbrot(int width, int height, double x1, double y1, double x2, double y2, int mi){
		xstart=x1; ystart=y1; xend=x2; yend=y2; maxiter=mi;
		this.width = width;
		this.height = height;
		data=new int[width*height];
	}
  
	private int iterate (double x, double y){
		int iter = 0;
		double aold; 
		double bold; 
		//double a2old = Double.MAX_VALUE;
        //double b2old = Double.MAX_VALUE; 
        double zsquared;
        double a = 0;
        double b=0,asquared = 0;
        double bsquared = 0;
		aold = 0; 
		bold = 0;
		asquared = a * a;
		a = x;
		bsquared = b * b;
		b = y;
		zsquared = asquared + bsquared;
		
		for (iter=0; iter<maxiter; iter++){
			a = asquared - bsquared + x;
			asquared = a * a;
			b = 2 * aold * b + y;
			
			if (bold == b && aold == a)
				iter= maxiter - 1;
			
			bsquared = b * b;
			zsquared = asquared + bsquared;

			if (zsquared > 4)
				break;
			
			bold = b; 
			aold = a;
		}
		
		return iter;
	}
  
	public int[] calculate(){
		int i = 0;
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				data[i++] = iterate(
								xstart + (xend - xstart) * x / (width - 1),
								ystart + (yend - ystart) * y / (height - 1)) % 256;
			}
		}
		
		return data;
	}
}
