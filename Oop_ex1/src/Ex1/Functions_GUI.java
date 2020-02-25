package Ex1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import com.google.gson.Gson;


public class Functions_GUI extends ArrayList<function> implements functions {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList <function> flist;
	
	public Functions_GUI() {
		this.flist = new ArrayList<function>();
	}

	@Override
	public void initFromFile(String file) throws IOException {
		try { 
			FileReader fr = new FileReader(file); 
			BufferedReader br = new BufferedReader(fr);
			String str;
			str = br.readLine();
			while (str != null) {
				Polynom p = new Polynom (str);
				flist.add(p);
				str = br.readLine();
			}
			br.close();     
			fr.close();     
		}
		catch(IOException ex) {  
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
	}

	@Override
	public void saveToFile(String file) throws IOException {
		try { 
			FileWriter fw = new FileWriter(file);  
			PrintWriter outs = new PrintWriter(fw);
			Iterator <function> it = flist.iterator();
			while (it.hasNext()) {
				outs.println(it.next().toString());
			}
			outs.close(); 
			fw.close();
		}
		catch(IOException ex) {  
			System.out.print("Error writing file\n" + ex);
		}
	}

	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		StdDraw.setPenRadius(0.005);
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(rx.get_min(), rx.get_max());
		StdDraw.setYscale(ry.get_min(), ry.get_max());
		Iterator <function> it = flist.iterator();
		while (it.hasNext()) {
			double xsteps = (rx.get_max()-rx.get_min())/resolution;
			double x = rx.get_min();
			for (int i = 0; i < resolution; i++) {
				function f =  it.next();
				double y = f.f(x);
				StdDraw.point(x, y);
				x += xsteps;
			}
		}
	}

	@Override
	public void drawFunctions(String json_file) {
		Gson gson = new Gson();
		 
		try 
		{
			File tempFile = new File(json_file);
			boolean exists = tempFile.exists();
			if(!exists) {
				System.out.println( json_file+" Dosn't exists");
				return;
			}
			FileReader reader = new FileReader(json_file);
			GUI_Tab tab = gson.fromJson(reader,GUI_Tab.class);
			Range rx=new Range(tab.Range_X[0],tab.Range_X[1]);
			Range ry=new Range(tab.Range_Y[0],tab.Range_Y[1]);
			this.drawFunctions(tab.Width, tab.Height, rx, ry, tab.Resolution);
		} 
		catch (FileNotFoundException e) {	
			e.printStackTrace();
		}
	}
	
	public class GUI_Tab 
	{

		public int Width;
		public int Height;
		public int Resolution;
		public double []Range_X;
		public double []Range_Y;

		public GUI_Tab(int width,int height,int resolution,double []Range_X,double []Range_Y)
		{
			Range_Y=new double[2];
			for (int i = 0; i < 3; i++) {
				this.Range_X[i]=Range_X[i];
				this.Range_Y[i]=Range_Y[i];
			}
			
			this.Height=height;
			this.Width=width;
			this.Resolution=resolution;
		}

	}
}
