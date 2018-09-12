package microestrutura;

import java.util.Scanner;
import javax.swing.WindowConstants;

/**
 *
 * @author pedro
 */
public class MicroEstrutura {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        
        System.out.println("T");
        int T= s.nextInt();
        System.out.println("h");
        float h= s.nextFloat();
        System.out.println("alfa");
        float alfa= s.nextFloat();
        System.out.println("dR");
        float dR= s.nextFloat();
        System.out.println("dR0");
        float dR0= s.nextFloat();
        //Riblet r = new Riblet();
        Riblet r = new Riblet( T, h, alfa, dR, dR0);
        r.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        r.setSize(820,620);
        r.setVisible(true);
    }
    
}
