/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings;

import Settings.exceptions.NonexistentEntityException;
import dbManagment.Klinika;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dbManagment.Pacienti;
import java.util.ArrayList;
import java.util.List;
import dbManagment.Staff;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Egzon
 */
public class KlinikaJpaController implements Serializable {

    public KlinikaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Klinika klinika) {
        if (klinika.getPacientiList() == null) {
            klinika.setPacientiList(new ArrayList<Pacienti>());
        }
        if (klinika.getStaffList() == null) {
            klinika.setStaffList(new ArrayList<Staff>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pacienti> attachedPacientiList = new ArrayList<Pacienti>();
            for (Pacienti pacientiListPacientiToAttach : klinika.getPacientiList()) {
                pacientiListPacientiToAttach = em.getReference(pacientiListPacientiToAttach.getClass(), pacientiListPacientiToAttach.getPId());
                attachedPacientiList.add(pacientiListPacientiToAttach);
            }
            klinika.setPacientiList(attachedPacientiList);
            List<Staff> attachedStaffList = new ArrayList<Staff>();
            for (Staff staffListStaffToAttach : klinika.getStaffList()) {
                staffListStaffToAttach = em.getReference(staffListStaffToAttach.getClass(), staffListStaffToAttach.getSId());
                attachedStaffList.add(staffListStaffToAttach);
            }
            klinika.setStaffList(attachedStaffList);
            em.persist(klinika);
            for (Pacienti pacientiListPacienti : klinika.getPacientiList()) {
                Klinika oldKlinikaIDOfPacientiListPacienti = pacientiListPacienti.getKlinikaID();
                pacientiListPacienti.setKlinikaID(klinika);
                pacientiListPacienti = em.merge(pacientiListPacienti);
                if (oldKlinikaIDOfPacientiListPacienti != null) {
                    oldKlinikaIDOfPacientiListPacienti.getPacientiList().remove(pacientiListPacienti);
                    oldKlinikaIDOfPacientiListPacienti = em.merge(oldKlinikaIDOfPacientiListPacienti);
                }
            }
            for (Staff staffListStaff : klinika.getStaffList()) {
                Klinika oldKlinikaIDOfStaffListStaff = staffListStaff.getKlinikaID();
                staffListStaff.setKlinikaID(klinika);
                staffListStaff = em.merge(staffListStaff);
                if (oldKlinikaIDOfStaffListStaff != null) {
                    oldKlinikaIDOfStaffListStaff.getStaffList().remove(staffListStaff);
                    oldKlinikaIDOfStaffListStaff = em.merge(oldKlinikaIDOfStaffListStaff);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Klinika klinika) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Klinika persistentKlinika = em.find(Klinika.class, klinika.getKId());
            List<Pacienti> pacientiListOld = persistentKlinika.getPacientiList();
            List<Pacienti> pacientiListNew = klinika.getPacientiList();
            List<Staff> staffListOld = persistentKlinika.getStaffList();
            List<Staff> staffListNew = klinika.getStaffList();
            List<Pacienti> attachedPacientiListNew = new ArrayList<Pacienti>();
            for (Pacienti pacientiListNewPacientiToAttach : pacientiListNew) {
                pacientiListNewPacientiToAttach = em.getReference(pacientiListNewPacientiToAttach.getClass(), pacientiListNewPacientiToAttach.getPId());
                attachedPacientiListNew.add(pacientiListNewPacientiToAttach);
            }
            pacientiListNew = attachedPacientiListNew;
            klinika.setPacientiList(pacientiListNew);
            List<Staff> attachedStaffListNew = new ArrayList<Staff>();
            for (Staff staffListNewStaffToAttach : staffListNew) {
                staffListNewStaffToAttach = em.getReference(staffListNewStaffToAttach.getClass(), staffListNewStaffToAttach.getSId());
                attachedStaffListNew.add(staffListNewStaffToAttach);
            }
            staffListNew = attachedStaffListNew;
            klinika.setStaffList(staffListNew);
            klinika = em.merge(klinika);
            for (Pacienti pacientiListOldPacienti : pacientiListOld) {
                if (!pacientiListNew.contains(pacientiListOldPacienti)) {
                    pacientiListOldPacienti.setKlinikaID(null);
                    pacientiListOldPacienti = em.merge(pacientiListOldPacienti);
                }
            }
            for (Pacienti pacientiListNewPacienti : pacientiListNew) {
                if (!pacientiListOld.contains(pacientiListNewPacienti)) {
                    Klinika oldKlinikaIDOfPacientiListNewPacienti = pacientiListNewPacienti.getKlinikaID();
                    pacientiListNewPacienti.setKlinikaID(klinika);
                    pacientiListNewPacienti = em.merge(pacientiListNewPacienti);
                    if (oldKlinikaIDOfPacientiListNewPacienti != null && !oldKlinikaIDOfPacientiListNewPacienti.equals(klinika)) {
                        oldKlinikaIDOfPacientiListNewPacienti.getPacientiList().remove(pacientiListNewPacienti);
                        oldKlinikaIDOfPacientiListNewPacienti = em.merge(oldKlinikaIDOfPacientiListNewPacienti);
                    }
                }
            }
            for (Staff staffListOldStaff : staffListOld) {
                if (!staffListNew.contains(staffListOldStaff)) {
                    staffListOldStaff.setKlinikaID(null);
                    staffListOldStaff = em.merge(staffListOldStaff);
                }
            }
            for (Staff staffListNewStaff : staffListNew) {
                if (!staffListOld.contains(staffListNewStaff)) {
                    Klinika oldKlinikaIDOfStaffListNewStaff = staffListNewStaff.getKlinikaID();
                    staffListNewStaff.setKlinikaID(klinika);
                    staffListNewStaff = em.merge(staffListNewStaff);
                    if (oldKlinikaIDOfStaffListNewStaff != null && !oldKlinikaIDOfStaffListNewStaff.equals(klinika)) {
                        oldKlinikaIDOfStaffListNewStaff.getStaffList().remove(staffListNewStaff);
                        oldKlinikaIDOfStaffListNewStaff = em.merge(oldKlinikaIDOfStaffListNewStaff);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = klinika.getKId();
                if (findKlinika(id) == null) {
                    throw new NonexistentEntityException("The klinika with id " + id + " no longer exists.");
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
            Klinika klinika;
            try {
                klinika = em.getReference(Klinika.class, id);
                klinika.getKId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The klinika with id " + id + " no longer exists.", enfe);
            }
            List<Pacienti> pacientiList = klinika.getPacientiList();
            for (Pacienti pacientiListPacienti : pacientiList) {
                pacientiListPacienti.setKlinikaID(null);
                pacientiListPacienti = em.merge(pacientiListPacienti);
            }
            List<Staff> staffList = klinika.getStaffList();
            for (Staff staffListStaff : staffList) {
                staffListStaff.setKlinikaID(null);
                staffListStaff = em.merge(staffListStaff);
            }
            em.remove(klinika);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Klinika> findKlinikaEntities() {
        return findKlinikaEntities(true, -1, -1);
    }

    public List<Klinika> findKlinikaEntities(int maxResults, int firstResult) {
        return findKlinikaEntities(false, maxResults, firstResult);
    }

    private List<Klinika> findKlinikaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Klinika.class));
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

    public Klinika findKlinika(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Klinika.class, id);
        } finally {
            em.close();
        }
    }

    public int getKlinikaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Klinika> rt = cq.from(Klinika.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
