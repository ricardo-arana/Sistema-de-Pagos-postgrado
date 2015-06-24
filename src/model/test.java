/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.UserJpaController;
import controller.entityMan;

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
        User u = new User();
        u.setIdUser(1);
        UserJpaController UserJ = new UserJpaController(entityMan.getInstance());
        u = UserJ.findUser(u.getIdUser());
        
        System.out.println(""+u.getUsername());
    }
    
}
