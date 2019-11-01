/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbManagment;

import Settings.exceptions.NonexistentEntityException;
import Settings.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dbManagment.Pacienti;
import dbManagment.Pagesat;
import dbManagment.Staff;
import dbManagment.Sherbimet;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Egzon
 */
public class PagesatJpaController implements Serializable {

    public PagesatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pagesat pagesat) throws PreexistingEntityException, Exception {
        if (pagesat.getSherbimetList() == null) {
            pagesat.setSherbimetList(new ArrayList<Sherbimet>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pacienti pacID = pagesat.getPacID();
            if (pacID != null) {
                pacID = em.getReference(pacID.getClass(), pacID.getPId());
                pagesat.setPacID(pacID);
            }
            Staff staffID = pagesat.getStaffID();
            if (staffID != null) {
                staffID = em.getReference(staffID.getClass(), staffID.getSId());
                pagesat.setStaffID(staffID);
            }
            List<Sherbimet> attachedSherbimetList = new ArrayList<Sherbimet>();
            for (Sherbimet sherbimetListSherbimetToAttach : pagesat.getSherbimetList()) {
                sherbimetListSherbimetToAttach = em.getReference(sherbimetListSherbimetToAttach.getClass(), sherbimetListSherbimetToAttach.getSherbimetID());
                attachedSherbimetList.add(sherbimetListSherbimetToAttach);
            }
            pagesat.setSherbimetList(attachedSherbimetList);
            em.persist(pagesat);
            if (pacID != null) {
                pacID.getPagesatList().add(pagesat);
                pacID = em.merge(pacID);
            }
            if (staffID != null) {
                staffID.getPagesatList().add(pagesat);
                staffID = em.merge(staffID);
            }
            for (Sherbimet sherbimetListSherbimet : pagesat.getSherbimetList()) {
                Pagesat oldPagesaIDOfSherbimetListSherbimet = sherbimetListSherbimet.getPagesaID();
                sherbimetListSherbimet.setPagesaID(pagesat);
                sherbimetListSherbimet = em.merge(sherbimetListSherbimet);
                if (oldPagesaIDOfSherbimetListSherbimet != null) {
                    oldPagesaIDOfSherbimetListSherbimet.getSherbimetList().remove(sherbimetListSherbimet);
                    oldPagesaIDOfSherbimetListSherbimet = em.merge(oldPagesaIDOfSherbimetListSherbimet);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPagesat(pagesat.getPagesaID()) != null) {
                throw new PreexistingEntityException("Pagesat " + pagesat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pagesat pagesat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagesat persistentPagesat = em.find(Pagesat.class, pagesat.getPagesaID());
            Pacienti pacIDOld = persistentPagesat.getPacID();
            Pacienti pacIDNew = pagesat.getPacID();
            Staff staffIDOld = persistentPagesat.getStaffID();
            Staff staffIDNew = pagesat.getStaffID();
            List<Sherbimet> sherbimetListOld = persistentPagesat.getSherbimetList();
            List<Sherbimet> sherbimetListNew = pagesat.getSherbimetList();
            if (pacIDNew != null) {
                pacIDNew = em.getReference(pacIDNew.getClass(), pacIDNew.getPId());
                pagesat.setPacID(pacIDNew);
            }
            if (staffIDNew != null) {
                staffIDNew = em.getReference(staffIDNew.getClass(), staffIDNew.getSId());
                pagesat.setStaffID(staffIDNew);
            }
            List<Sherbimet> attachedSherbimetListNew = new ArrayList<Sherbimet>();
            for (Sherbimet sherbimetListNewSherbimetToAttach : sherbimetListNew) {
                if(sherbimetListNewSherbimetToAttach != null){
                       sherbimetListNewSherbimetToAttach = em.getReference(sherbimetListNewSherbimetToAttach.getClass(), sherbimetListNewSherbimetToAttach.getSherbimetID());
                attachedSherbimetListNew.add(sherbimetListNewSherbimetToAttach);
                }
             
            }
            sherbimetListNew = attachedSherbimetListNew;
            pagesat.setSherbimetList(sherbimetListNew);
            pagesat = em.merge(pagesat);
            if (pacIDOld != null && !pacIDOld.equals(pacIDNew)) {
                pacIDOld.getPagesatList().remove(pagesat);
                pacIDOld = em.merge(pacIDOld);
            }
            if (pacIDNew != null && !pacIDNew.equals(pacIDOld)) {
                pacIDNew.getPagesatList().add(pagesat);
                pacIDNew = em.merge(pacIDNew);
            }
            if (staffIDOld != null && !staffIDOld.equals(staffIDNew)) {
                staffIDOld.getPagesatList().remove(pagesat);
                staffIDOld = em.merge(staffIDOld);
            }
            if (staffIDNew != null && !staffIDNew.equals(staffIDOld)) {
                staffIDNew.getPagesatList().add(pagesat);
                staffIDNew = em.merge(staffIDNew);
            }
            for (Sherbimet sherbimetListOldSherbimet : sherbimetListOld) {
                if (!sherbimetListNew.contains(sherbimetListOldSherbimet)) {
                    sherbimetListOldSherbimet.setPagesaID(null);
                    sherbimetListOldSherbimet = em.merge(sherbimetListOldSherbimet);
                }
            }
//            for (Sherbimet sherbimetListNewSherbimet : sherbimetListNew) {
//                if (!sherbimetListOld.contains(sherbimetListNewSherbimet)) {
//                    Pagesat oldPagesaIDOfSherbimetListNewSherbimet = sherbimetListNewSherbimet.getPagesaID();
//                    sherbimetListNewSherbimet.setPagesaID(pagesat);
//                    sherbimetListNewSherbimet = em.merge(sherbimetListNewSherbimet);
//                    if (oldPagesaIDOfSherbimetListNewSherbimet != null && !oldPagesaIDOfSherbimetListNewSherbimet.equals(pagesat)) {
//                        oldPagesaIDOfSherbimetListNewSherbimet.getSherbimetList().remove(sherbimetListNewSherbimet);
//                        oldPagesaIDOfSherbimetListNewSherbimet = em.merge(oldPagesaIDOfSherbimetListNewSherbimet);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagesat.getPagesaID();
                if (findPagesat(id) == null) {
                    throw new NonexistentEntityException("The pagesat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagesat pagesat;
            try {
                pagesat = em.getReference(Pagesat.class, id);
                pagesat.getPagesaID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagesat with id " + id + " no longer exists.", enfe);
            }
            Pacienti pacID = pagesat.getPacID();
            if (pacID != null) {
                pacID.getPagesatList().remove(pagesat);
                pacID = em.merge(pacID);
            }
            Staff staffID = pagesat.getStaffID();
            if (staffID != null) {
                staffID.getPagesatList().remove(pagesat);
                staffID = em.merge(staffID);
            }
            List<Sherbimet> sherbimetList = pagesat.getSherbimetList();
            for (Sherbimet sherbimetListSherbimet : sherbimetList) {
                sherbimetListSherbimet.setPagesaID(null);
                sherbimetListSherbimet = em.merge(sherbimetListSherbimet);
            }
            em.remove(pagesat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pagesat> findPagesatEntities() {
        return findPagesatEntities(true, -1, -1);
    }

    public List<Pagesat> findPagesatEntities(int maxResults, int firstResult) {
        return findPagesatEntities(false, maxResults, firstResult);
    }

    private List<Pagesat> findPagesatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pagesat.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Pagesat findPagesat(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pagesat.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagesatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pagesat> rt = cq.from(Pagesat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
