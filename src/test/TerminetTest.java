/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import dbManagment.*;
import java.util.List;

/**
 *
 * @author Egzon
 */
public class TerminetTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PolyClinicManagmentV2.0PU");
        StaffJpaController tjpa = new StaffJpaController(emf);
       List<Staff> terList = tjpa.findStaffEntities();
       for(Staff t : terList){
          Staff ter = t;
           System.out.println(ter.getEmri());
       }
                
    }
}
