
package campo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Campinho extends JFrame{

    
    BufferedImage bi;
    int mult;
    int tamanhoCampoX;
    int tamanhoCampoY;
    int xTopo;
    int yTopo;
    
    int meioCampoX;   
    int meioCampoY;
    
    int raioMeioCampo;
    
    int tamanhoEscanteio;
    int tamanhoGrandeAreaX;
    int tamanhoGrandeAreaY;
    
    int tamanhoPeqAreaX;
    int tamanhoPeqAreaY;
    
    int raioCirculograndeArea;

    int penaltiX;
    int diametroMarcas;
    int tamCirculoGrandeArea;
    
    public Campinho(){
        mult = 5;
        tamanhoCampoX = 120 * mult;
        tamanhoCampoY = 80 * mult; 
        
        xTopo= 40;
        yTopo= 50;
        
        meioCampoX = (int) (tamanhoCampoX/2) + xTopo;
        meioCampoY = (int) (tamanhoCampoY/2)+ yTopo;
        
        raioMeioCampo = (int)(0.2*tamanhoCampoX);
        
        tamanhoEscanteio= 3 *mult;
        
        tamanhoGrandeAreaX = (int) (0.15*tamanhoCampoX);
        tamanhoGrandeAreaY = (int) (0.6*tamanhoCampoY);
        
        tamanhoPeqAreaX = (int) (0.06*tamanhoCampoX);
        tamanhoPeqAreaY = (int) (0.24*tamanhoCampoY );
        
        penaltiX= (int) (( tamanhoGrandeAreaX + tamanhoPeqAreaX )/2)+xTopo;
        
        diametroMarcas = 6;
        tamCirculoGrandeArea = 10 * mult;
    }


    @Override
    public void paint(Graphics g){

        //mageIO iio;
        //iio = new ImageIO();
        try {
            bi = ImageIO.read(new File("grama.jpeg"));

        } catch (IOException ex) {
            Logger.getLogger(Campinho.class.getName()).log(Level.SEVERE, null, ex);
        }

        Graphics2D g2d = (Graphics2D) g;

        //g2d.drawImage(bi,0,0,null);
        GeneralPath linha = new GeneralPath();
        GeneralPath linha2 = new GeneralPath();
        Rectangle2D.Double r = new Rectangle2D.Double(  xTopo,yTopo ,
                                                        tamanhoCampoX,
                                                        tamanhoCampoY);
        //linha do meio de campo
        linha.moveTo(meioCampoX, yTopo);
        linha.lineTo(meioCampoX, yTopo+tamanhoCampoY);
        
        //linha de escanteio
        //sup esq
        linha.moveTo(xTopo+tamanhoEscanteio,yTopo );
        linha.quadTo(xTopo+tamanhoEscanteio,yTopo+tamanhoEscanteio , xTopo, yTopo+tamanhoEscanteio);
        
        //inf esq
        linha.moveTo(xTopo,yTopo + tamanhoCampoY - tamanhoEscanteio);
        linha.quadTo(xTopo+tamanhoEscanteio,yTopo+tamanhoCampoY-tamanhoEscanteio , xTopo+tamanhoEscanteio, yTopo+tamanhoCampoY);
        
        //sup dir
        linha.moveTo(xTopo+tamanhoCampoX-tamanhoEscanteio,yTopo );
        linha.quadTo(xTopo+tamanhoCampoX-tamanhoEscanteio,yTopo+tamanhoEscanteio , xTopo+tamanhoCampoX, yTopo+tamanhoEscanteio);
        
        //inf esq
        linha.moveTo(xTopo+tamanhoCampoX,yTopo + tamanhoCampoY - tamanhoEscanteio);
        linha.quadTo(xTopo+tamanhoCampoX-tamanhoEscanteio,yTopo+tamanhoCampoY-tamanhoEscanteio , xTopo+tamanhoCampoX-tamanhoEscanteio, yTopo+tamanhoCampoY);
        
        //fazer as grandes areas esq
        linha.moveTo(xTopo, yTopo + (tamanhoCampoY/2) - (tamanhoGrandeAreaY/2));
        linha.lineTo(xTopo+tamanhoGrandeAreaX, yTopo+(tamanhoCampoY/2)- (tamanhoGrandeAreaY/2));
        linha.lineTo(xTopo+tamanhoGrandeAreaX, yTopo+(tamanhoCampoY/2)+ (tamanhoGrandeAreaY/2) );
        linha.lineTo(xTopo, yTopo+(tamanhoCampoY/2)+ (tamanhoGrandeAreaY/2) );
        
        //fazer as grandes areas dir
        linha.moveTo(xTopo + tamanhoCampoX, yTopo + (tamanhoCampoY/2) - (tamanhoGrandeAreaY/2));
        linha.lineTo(xTopo+tamanhoCampoX-tamanhoGrandeAreaX, yTopo+(tamanhoCampoY/2)- (tamanhoGrandeAreaY/2));
        linha.lineTo(xTopo+tamanhoCampoX-tamanhoGrandeAreaX, yTopo+(tamanhoCampoY/2)+ (tamanhoGrandeAreaY/2) );
        linha.lineTo(xTopo+tamanhoCampoX, yTopo+(tamanhoCampoY/2)+ (tamanhoGrandeAreaY/2) );
        
        //fazer as peq areas esq
        linha.moveTo(xTopo, yTopo + (tamanhoCampoY/2) - (tamanhoPeqAreaY/2));
        linha.lineTo(xTopo+tamanhoPeqAreaX, yTopo+(tamanhoCampoY/2)- (tamanhoPeqAreaY/2));
        linha.lineTo(xTopo+tamanhoPeqAreaX, yTopo+(tamanhoCampoY/2)+ (tamanhoPeqAreaY/2) );
        linha.lineTo(xTopo, yTopo+(tamanhoCampoY/2)+ (tamanhoPeqAreaY/2) );
        
        //fazer as grandes areas dir
        linha.moveTo(xTopo + tamanhoCampoX, yTopo + (tamanhoCampoY/2) - (tamanhoPeqAreaY/2));
        linha.lineTo(xTopo+tamanhoCampoX-tamanhoPeqAreaX, yTopo+(tamanhoCampoY/2)- (tamanhoPeqAreaY/2));
        linha.lineTo(xTopo+tamanhoCampoX-tamanhoPeqAreaX, yTopo+(tamanhoCampoY/2)+ (tamanhoPeqAreaY/2) );
        linha.lineTo(xTopo+tamanhoCampoX, yTopo+(tamanhoCampoY/2)+ (tamanhoPeqAreaY/2) );
        
        //circulo do meio
        Ellipse2D.Float meioCampo;
        meioCampo = new Ellipse2D.Float((float)(meioCampoX-(raioMeioCampo/2)),(float) (meioCampoY-(raioMeioCampo/2)),(float) (raioMeioCampo), (float)(raioMeioCampo));
        
        //fazer o circulo da grande area
        linha2.moveTo(xTopo+tamanhoGrandeAreaX, ( (meioCampoY) - (tamCirculoGrandeArea)));
        
        linha2.quadTo(xTopo+tamanhoGrandeAreaX+60, (meioCampoY),   xTopo+tamanhoGrandeAreaX, (meioCampoY) + (tamCirculoGrandeArea));
        
        
        //texto
        
        
        
        //fazer a marca do penalti
        Ellipse2D.Float marcaPenalti = new Ellipse2D.Float( penaltiX-(diametroMarcas/2), meioCampoY-(diametroMarcas/2),diametroMarcas, diametroMarcas);
        
        //fazer a Marcacao do meio de campo
        Ellipse2D.Float marcaMeio = new Ellipse2D.Float( meioCampoX-(diametroMarcas/2), meioCampoY-(diametroMarcas/2),diametroMarcas, diametroMarcas);
        
        AffineTransform rotation = new AffineTransform();
        rotation.setToRotation(Math.PI, meioCampoX, meioCampoY);     
        
        g2d.setColor(Color.white);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(r);
        

        TexturePaint tp=new TexturePaint(bi, new Rectangle(0,0,bi.getWidth(), 
                                                            bi.getHeight()));
        g2d.setPaint(tp);
        g2d.fill(r);
        g2d.setColor(Color.white);
        
        //desenhar as linhas
        g2d.draw(linha);
        g2d.draw(linha2);
        g2d.draw(rotation.createTransformedShape(linha2));
        //circulo central
        g2d.draw(meioCampo);
        g2d.fill(marcaPenalti);
        g2d.fill(rotation.createTransformedShape(marcaPenalti));
        g2d.fill(marcaMeio);
        
        //texto
        Font font = new Font("Serif", Font.BOLD, mult*3);
        g2d.setFont(font);
        
        g2d.setColor(Color.BLACK);
        AffineTransform at = new AffineTransform();
        //at.setToRotation(Math.PI/2, meioCampoX,meioCampoY);
        for(int i=0;i<5;i++){
            at.setToRotation(i*(Math.PI/2), meioCampoX,meioCampoY);
            if(i==0){
                
            }else  if((i%2)==0){
                g2d.drawString("OPERARIO",meioCampoX-12*mult, yTopo-21*mult );
            }else{
                g2d.drawString("CAMPEÃO",meioCampoX-8*mult, yTopo-10 );
            }
            g2d.setTransform(at);
        }
        
        
    }

}
