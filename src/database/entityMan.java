/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 *
 * @author Ricardo
 */
public class entityMan {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemasPagosPGPU");

    public entityMan(){
       
    }
    
    public static EntityManagerFactory getInstance(){
        return emf;
    }
    
}
