/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dbManagment.*;
import dbManagment.StaffJpaController;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Egzon
 */
public class Test3 {
    public static void main(String[] args) {
           EntityManagerFactory emf = Persistence.createEntityManagerFactory("PolyClinicManagmentV2.0PU");
        TerminetJpaController tjpa = new TerminetJpaController(emf);
       List<Terminet> terList = tjpa.findTerminetEntities();
         Staff s = null;
       for(Terminet t : terList){
          Terminet ter = t;
         s = ter.getStaffID();
          if(s.getSId() !=null){
               System.out.println(s.getSId());
          }
          
       }
    }
}
