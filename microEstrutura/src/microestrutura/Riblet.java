

package microestrutura;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import javax.swing.JFrame;

/**
 *
 * @author pedro
 */
class Riblet extends JFrame{
    int T;
    float alfa, x,y, x0,y0;
    float xR, dR0, dR, h;
    
    public Riblet(){
        T= 3;
        alfa = 30;
        alfa = (float) ((alfa/180)*Math.PI);
        x0= 100;
        y0 = 150;
        dR0 = 200;
        dR = 300;
        h = 100;
        
        xR= (float) (2*h*Math.tan(alfa));
    }
    public Riblet(int T,float h, float alfa, float dR, float dR0){
        x0= 100;
        y0 = 150;
        this.T = T;
        this.h = h;
        alfa = (float) ((alfa/180)*Math.PI);
        this.alfa = alfa;
        this.dR = dR;
        this.dR0 = dR0;  
        
        xR= (float) (2*h*Math.tan(alfa));
    }

    @Override
    public void paint(Graphics g){
        GeneralPath linha = new GeneralPath();
        Graphics2D g2d = (Graphics2D) g;
        //0
        linha.moveTo(x0, y0);
        x= x0+(dR0-(xR/2));
        y= y0;
        
        for(int i=0; i<T; i++){
            //1
            linha.lineTo(x,y);
            x= x +(xR/2);
            y= y0-h;
            //2
            linha.lineTo(x, y);
            x= x +(xR/2);
            y= y0;
            //3
            linha.lineTo(x, y);
            x= x+(dR-xR);
            //4
            linha.lineTo(x, y);
        }
        //x= x+(xR/2);
        //y= y0-h;
        //5
        //linha.lineTo(x, y);
        //x= x+(xR/2);
        //y= y0;
        //6
        //linha.lineTo(x, y);
        
        x =x+30;
        linha.lineTo(x, y);
        
        
        g2d.draw(linha);
    }
    
    
}
