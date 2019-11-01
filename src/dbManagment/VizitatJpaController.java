/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbManagment;

import dbManagment.exceptions.NonexistentEntityException;
import dbManagment.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Egzon
 */
public class VizitatJpaController implements Serializable {

    public VizitatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vizitat vizitat) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pacienti pacID = vizitat.getPacID();
            if (pacID != null) {
                pacID = em.getReference(pacID.getClass(), pacID.getPId());
                vizitat.setPacID(pacID);
            }
            Staff staffID = vizitat.getStaffID();
            if (staffID != null) {
                staffID = em.getReference(staffID.getClass(), staffID.getSId());
                vizitat.setStaffID(staffID);
            }
            em.persist(vizitat);
            if (pacID != null) {
                pacID.getVizitatList().add(vizitat);
                pacID = em.merge(pacID);
            }
            if (staffID != null) {
                staffID.getVizitatList().add(vizitat);
                staffID = em.merge(staffID);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVizitat(vizitat.getVId()) != null) {
                throw new PreexistingEntityException("Vizitat " + vizitat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vizitat vizitat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vizitat persistentVizitat = em.find(Vizitat.class, vizitat.getVId());
            Pacienti pacIDOld = persistentVizitat.getPacID();
            Pacienti pacIDNew = vizitat.getPacID();
            Staff staffIDOld = persistentVizitat.getStaffID();
            Staff staffIDNew = vizitat.getStaffID();
            if (pacIDNew != null) {
                pacIDNew = em.getReference(pacIDNew.getClass(), pacIDNew.getPId());
                vizitat.setPacID(pacIDNew);
            }
            if (staffIDNew != null) {
                staffIDNew = em.getReference(staffIDNew.getClass(), staffIDNew.getSId());
                vizitat.setStaffID(staffIDNew);
            }
            vizitat = em.merge(vizitat);
            if (pacIDOld != null && !pacIDOld.equals(pacIDNew)) {
                pacIDOld.getVizitatList().remove(vizitat);
                pacIDOld = em.merge(pacIDOld);
            }
            if (pacIDNew != null && !pacIDNew.equals(pacIDOld)) {
                pacIDNew.getVizitatList().add(vizitat);
                pacIDNew = em.merge(pacIDNew);
            }
            if (staffIDOld != null && !staffIDOld.equals(staffIDNew)) {
                staffIDOld.getVizitatList().remove(vizitat);
                staffIDOld = em.merge(staffIDOld);
            }
            if (staffIDNew != null && !staffIDNew.equals(staffIDOld)) {
                staffIDNew.getVizitatList().add(vizitat);
                staffIDNew = em.merge(staffIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vizitat.getVId();
                if (findVizitat(id) == null) {
                    throw new NonexistentEntityException("The vizitat with id " + id + " no longer exists.");
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
            Vizitat vizitat;
            try {
                vizitat = em.getReference(Vizitat.class, id);
                vizitat.getVId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vizitat with id " + id + " no longer exists.", enfe);
            }
            Pacienti pacID = vizitat.getPacID();
            if (pacID != null) {
                pacID.getVizitatList().remove(vizitat);
                pacID = em.merge(pacID);
            }
            Staff staffID = vizitat.getStaffID();
            if (staffID != null) {
                staffID.getVizitatList().remove(vizitat);
                staffID = em.merge(staffID);
            }
            em.remove(vizitat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vizitat> findVizitatEntities() {
        return findVizitatEntities(true, -1, -1);
    }

    public List<Vizitat> findVizitatEntities(int maxResults, int firstResult) {
        return findVizitatEntities(false, maxResults, firstResult);
    }

    private List<Vizitat> findVizitatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vizitat.class));
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

    public Vizitat findVizitat(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vizitat.class, id);
        } finally {
            em.close();
        }
    }

    public int getVizitatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vizitat> rt = cq.from(Vizitat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
