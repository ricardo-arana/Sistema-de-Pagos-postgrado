/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.MaestriaJpaController;
import controller.entityMan;
import java.util.List;
import model.Maestria;

/**
 *
 * @author Ricardo
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        MaestriaJpaController mjc = new MaestriaJpaController(entityMan.getInstance());
        
        List<Maestria> lista = mjc.findMaestriaEntities();
        for(Maestria m: lista){
            System.out.println(m);
        }
    }
    
}
