/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campo;

import javax.swing.WindowConstants;

/**
 *
 * @author pedro
 */
public class Campo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Campinho f = new Campinho(); 
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(820,620);
        f.setVisible(true);

    }
    
}
