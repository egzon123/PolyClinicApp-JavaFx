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
import dbManagment.Pagesat;
import dbManagment.Sherbimet;
import dbManagment.Staff;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Egzon
 */
public class SherbimetJpaController implements Serializable {

    public SherbimetJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sherbimet sherbimet) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagesat pagesaID = sherbimet.getPagesaID();
            if (pagesaID != null) {
                pagesaID = em.getReference(pagesaID.getClass(), pagesaID.getPagesaID());
                sherbimet.setPagesaID(pagesaID);
            }
            Staff staffID = sherbimet.getStaffID();
            if (staffID != null) {
                staffID = em.getReference(staffID.getClass(), staffID.getSId());
                sherbimet.setStaffID(staffID);
            }
            em.persist(sherbimet);
            if (pagesaID != null) {
                pagesaID.getSherbimetList().add(sherbimet);
                pagesaID = em.merge(pagesaID);
            }
            if (staffID != null) {
                staffID.getSherbimetList().add(sherbimet);
                staffID = em.merge(staffID);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSherbimet(sherbimet.getSherbimetID()) != null) {
                throw new PreexistingEntityException("Sherbimet " + sherbimet + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sherbimet sherbimet) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sherbimet persistentSherbimet = em.find(Sherbimet.class, sherbimet.getSherbimetID());
            Pagesat pagesaIDOld = persistentSherbimet.getPagesaID();
            Pagesat pagesaIDNew = sherbimet.getPagesaID();
            Staff staffIDOld = persistentSherbimet.getStaffID();
            Staff staffIDNew = sherbimet.getStaffID();
            if (pagesaIDNew != null) {
                pagesaIDNew = em.getReference(pagesaIDNew.getClass(), pagesaIDNew.getPagesaID());
                sherbimet.setPagesaID(pagesaIDNew);
            }
            if (staffIDNew != null) {
                staffIDNew = em.getReference(staffIDNew.getClass(), staffIDNew.getSId());
                sherbimet.setStaffID(staffIDNew);
            }
            sherbimet = em.merge(sherbimet);
            if (pagesaIDOld != null && !pagesaIDOld.equals(pagesaIDNew)) {
                pagesaIDOld.getSherbimetList().remove(sherbimet);
                pagesaIDOld = em.merge(pagesaIDOld);
            }
            if (pagesaIDNew != null && !pagesaIDNew.equals(pagesaIDOld)) {
                pagesaIDNew.getSherbimetList().add(sherbimet);
                pagesaIDNew = em.merge(pagesaIDNew);
            }
            if (staffIDOld != null && !staffIDOld.equals(staffIDNew)) {
                staffIDOld.getSherbimetList().remove(sherbimet);
                staffIDOld = em.merge(staffIDOld);
            }
            if (staffIDNew != null && !staffIDNew.equals(staffIDOld)) {
                staffIDNew.getSherbimetList().add(sherbimet);
                staffIDNew = em.merge(staffIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sherbimet.getSherbimetID();
                if (findSherbimet(id) == null) {
                    throw new NonexistentEntityException("The sherbimet with id " + id + " no longer exists.");
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
            Sherbimet sherbimet;
            try {
                sherbimet = em.getReference(Sherbimet.class, id);
                sherbimet.getSherbimetID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sherbimet with id " + id + " no longer exists.", enfe);
            }
            Pagesat pagesaID = sherbimet.getPagesaID();
            if (pagesaID != null) {
                pagesaID.getSherbimetList().remove(sherbimet);
                pagesaID = em.merge(pagesaID);
            }
            Staff staffID = sherbimet.getStaffID();
            if (staffID != null) {
                staffID.getSherbimetList().remove(sherbimet);
                staffID = em.merge(staffID);
            }
            em.remove(sherbimet);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sherbimet> findSherbimetEntities() {
        return findSherbimetEntities(true, -1, -1);
    }

    public List<Sherbimet> findSherbimetEntities(int maxResults, int firstResult) {
        return findSherbimetEntities(false, maxResults, firstResult);
    }

    private List<Sherbimet> findSherbimetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sherbimet.class));
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

    public Sherbimet findSherbimet(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sherbimet.class, id);
        } finally {
            em.close();
        }
    }

    public int getSherbimetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sherbimet> rt = cq.from(Sherbimet.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
