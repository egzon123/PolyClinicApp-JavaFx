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
import dbManagment.Staff;
import dbManagment.Terminet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Egzon
 */
public class TerminetJpaController implements Serializable {

    public TerminetJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Terminet terminet) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Staff staffID = terminet.getStaffID();
            if (staffID != null) {
                staffID = em.getReference(staffID.getClass(), staffID.getSId());
                terminet.setStaffID(staffID);
            }
            em.persist(terminet);
            if (staffID != null) {
                staffID.getTerminetList().add(terminet);
                staffID = em.merge(staffID);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTerminet(terminet.getTId()) != null) {
                throw new PreexistingEntityException("Terminet " + terminet + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Terminet terminet) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Terminet persistentTerminet = em.find(Terminet.class, terminet.getTId());
            Staff staffIDOld = persistentTerminet.getStaffID();
            Staff staffIDNew = terminet.getStaffID();
            if (staffIDNew != null) {
                staffIDNew = em.getReference(staffIDNew.getClass(), staffIDNew.getSId());
                terminet.setStaffID(staffIDNew);
            }
            terminet = em.merge(terminet);
            if (staffIDOld != null && !staffIDOld.equals(staffIDNew)) {
                staffIDOld.getTerminetList().remove(terminet);
                staffIDOld = em.merge(staffIDOld);
            }
            if (staffIDNew != null && !staffIDNew.equals(staffIDOld)) {
                staffIDNew.getTerminetList().add(terminet);
                staffIDNew = em.merge(staffIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = terminet.getTId();
                if (findTerminet(id) == null) {
                    throw new NonexistentEntityException("The terminet with id " + id + " no longer exists.");
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
            Terminet terminet;
            try {
                terminet = em.getReference(Terminet.class, id);
                terminet.getTId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The terminet with id " + id + " no longer exists.", enfe);
            }
            Staff staffID = terminet.getStaffID();
            if (staffID != null) {
                staffID.getTerminetList().remove(terminet);
                staffID = em.merge(staffID);
            }
            em.remove(terminet);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Terminet> findTerminetEntities() {
        return findTerminetEntities(true, -1, -1);
    }

    public List<Terminet> findTerminetEntities(int maxResults, int firstResult) {
        return findTerminetEntities(false, maxResults, firstResult);
    }

    private List<Terminet> findTerminetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Terminet.class));
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

    public Terminet findTerminet(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Terminet.class, id);
        } finally {
            em.close();
        }
    }

    public int getTerminetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Terminet> rt = cq.from(Terminet.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
