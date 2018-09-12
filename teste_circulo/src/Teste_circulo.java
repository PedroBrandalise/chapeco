
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Scanner;

/**
 *
 * @author pedro
 */
public class Teste_circulo extends JFrame {
	int a,r;
	int b;
	public Teste_circulo(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//r=100;
		//a=100;
		//b = 100;
	} 

	@Override
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		
		
		
		int[] coord[]= desenhaCirculo(r, a, b);

		GeneralPath linha = new GeneralPath();

		AffineTransform rotation = new AffineTransform();

		rotation.setToRotation(Math.PI/4, a, b);

		linha.moveTo(coord[0][0], coord[1][0]);
		//System.out.println(coord[0].length);
		int i=0;
		while(i<coord[0].length){


			linha.moveTo(coord[0][i], coord[1][i]);
			linha.lineTo(coord[0][i], coord[1][i]);

			g2d.draw(linha);

			linha.transform(rotation);
			g2d.draw(linha);

			i++;
		}


	}

	public static int[][] desenhaCirculo(float raio, float cx,float cy){
		int x0 = Math.round(cx); 
		int y0 = Math.round(cy);
		int xfinal= (int) Math.round( raio* Math.sin(45));
		xfinal+= cx;


		int coord[][] = new int[2][xfinal+1];
		int x=x0, y=y0;

		int i=0;
		while(x<=xfinal){

			y=(int) Math.round(Math.sqrt( raio*raio  -(x-cx)*(x-cx) )+ cy);

			coord[0][i]=x;
			coord[1][i]=y;

			x++;
			i++;

		}

		return coord;
	}




}

