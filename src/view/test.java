/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.MaestriaJpaController;
import controller.MatriculaJpaController;
import controller.entityMan;
import java.util.List;
import model.Maestria;
import model.Matricula;

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
        MatriculaJpaController Mjc = new MatriculaJpaController(entityMan.getInstance());
        List<Matricula> listaCiclos = Mjc.findMatriculaEntities();
        for(Matricula m: listaCiclos){
            System.out.println(m);
        }
    }
    
}
