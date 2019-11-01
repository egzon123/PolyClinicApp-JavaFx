/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings;

import Settings.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dbManagment.Klinika;
import dbManagment.Pacienti;
import dbManagment.Staff;
import dbManagment.Pagesat;
import java.util.ArrayList;
import java.util.List;
import dbManagment.Vizitat;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Egzon
 */
public class PacientiJpaController implements Serializable {

    public PacientiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pacienti pacienti) {
        if (pacienti.getPagesatList() == null) {
            pacienti.setPagesatList(new ArrayList<Pagesat>());
        }
        if (pacienti.getVizitatList() == null) {
            pacienti.setVizitatList(new ArrayList<Vizitat>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Klinika klinikaID = pacienti.getKlinikaID();
            if (klinikaID != null) {
                klinikaID = em.getReference(klinikaID.getClass(), klinikaID.getKId());
                pacienti.setKlinikaID(klinikaID);
            }
            Staff staffID = pacienti.getStaffID();
            if (staffID != null) {
                staffID = em.getReference(staffID.getClass(), staffID.getSId());
                pacienti.setStaffID(staffID);
            }
            List<Pagesat> attachedPagesatList = new ArrayList<Pagesat>();
            for (Pagesat pagesatListPagesatToAttach : pacienti.getPagesatList()) {
                pagesatListPagesatToAttach = em.getReference(pagesatListPagesatToAttach.getClass(), pagesatListPagesatToAttach.getPagesaID());
                attachedPagesatList.add(pagesatListPagesatToAttach);
            }
            pacienti.setPagesatList(attachedPagesatList);
            List<Vizitat> attachedVizitatList = new ArrayList<Vizitat>();
            for (Vizitat vizitatListVizitatToAttach : pacienti.getVizitatList()) {
                vizitatListVizitatToAttach = em.getReference(vizitatListVizitatToAttach.getClass(), vizitatListVizitatToAttach.getVId());
                attachedVizitatList.add(vizitatListVizitatToAttach);
            }
            pacienti.setVizitatList(attachedVizitatList);
            em.persist(pacienti);
            if (klinikaID != null) {
                klinikaID.getPacientiList().add(pacienti);
                klinikaID = em.merge(klinikaID);
            }
            if (staffID != null) {
                staffID.getPacientiList().add(pacienti);
                staffID = em.merge(staffID);
            }
            for (Pagesat pagesatListPagesat : pacienti.getPagesatList()) {
                Pacienti oldPacIDOfPagesatListPagesat = pagesatListPagesat.getPacID();
                pagesatListPagesat.setPacID(pacienti);
                pagesatListPagesat = em.merge(pagesatListPagesat);
                if (oldPacIDOfPagesatListPagesat != null) {
                    oldPacIDOfPagesatListPagesat.getPagesatList().remove(pagesatListPagesat);
                    oldPacIDOfPagesatListPagesat = em.merge(oldPacIDOfPagesatListPagesat);
                }
            }
            for (Vizitat vizitatListVizitat : pacienti.getVizitatList()) {
                Pacienti oldPacIDOfVizitatListVizitat = vizitatListVizitat.getPacID();
                vizitatListVizitat.setPacID(pacienti);
                vizitatListVizitat = em.merge(vizitatListVizitat);
                if (oldPacIDOfVizitatListVizitat != null) {
                    oldPacIDOfVizitatListVizitat.getVizitatList().remove(vizitatListVizitat);
                    oldPacIDOfVizitatListVizitat = em.merge(oldPacIDOfVizitatListVizitat);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pacienti pacienti) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pacienti persistentPacienti = em.find(Pacienti.class, pacienti.getPId());
            Klinika klinikaIDOld = persistentPacienti.getKlinikaID();
            Klinika klinikaIDNew = pacienti.getKlinikaID();
            Staff staffIDOld = persistentPacienti.getStaffID();
            Staff staffIDNew = pacienti.getStaffID();
            List<Pagesat> pagesatListOld = persistentPacienti.getPagesatList();
            List<Pagesat> pagesatListNew = pacienti.getPagesatList();
            List<Vizitat> vizitatListOld = persistentPacienti.getVizitatList();
            List<Vizitat> vizitatListNew = pacienti.getVizitatList();
            if (klinikaIDNew != null) {
                klinikaIDNew = em.getReference(klinikaIDNew.getClass(), klinikaIDNew.getKId());
                pacienti.setKlinikaID(klinikaIDNew);
            }
            if (staffIDNew != null) {
                staffIDNew = em.getReference(staffIDNew.getClass(), staffIDNew.getSId());
                pacienti.setStaffID(staffIDNew);
            }
            List<Pagesat> attachedPagesatListNew = new ArrayList<Pagesat>();
            for (Pagesat pagesatListNewPagesatToAttach : pagesatListNew) {
                pagesatListNewPagesatToAttach = em.getReference(pagesatListNewPagesatToAttach.getClass(), pagesatListNewPagesatToAttach.getPagesaID());
                attachedPagesatListNew.add(pagesatListNewPagesatToAttach);
            }
            pagesatListNew = attachedPagesatListNew;
            pacienti.setPagesatList(pagesatListNew);
            List<Vizitat> attachedVizitatListNew = new ArrayList<Vizitat>();
            for (Vizitat vizitatListNewVizitatToAttach : vizitatListNew) {
                vizitatListNewVizitatToAttach = em.getReference(vizitatListNewVizitatToAttach.getClass(), vizitatListNewVizitatToAttach.getVId());
                attachedVizitatListNew.add(vizitatListNewVizitatToAttach);
            }
            vizitatListNew = attachedVizitatListNew;
            pacienti.setVizitatList(vizitatListNew);
            pacienti = em.merge(pacienti);
            if (klinikaIDOld != null && !klinikaIDOld.equals(klinikaIDNew)) {
                klinikaIDOld.getPacientiList().remove(pacienti);
                klinikaIDOld = em.merge(klinikaIDOld);
            }
            if (klinikaIDNew != null && !klinikaIDNew.equals(klinikaIDOld)) {
                klinikaIDNew.getPacientiList().add(pacienti);
                klinikaIDNew = em.merge(klinikaIDNew);
            }
            if (staffIDOld != null && !staffIDOld.equals(staffIDNew)) {
                staffIDOld.getPacientiList().remove(pacienti);
                staffIDOld = em.merge(staffIDOld);
            }
            if (staffIDNew != null && !staffIDNew.equals(staffIDOld)) {
                staffIDNew.getPacientiList().add(pacienti);
                staffIDNew = em.merge(staffIDNew);
            }
            for (Pagesat pagesatListOldPagesat : pagesatListOld) {
                if (!pagesatListNew.contains(pagesatListOldPagesat)) {
                    pagesatListOldPagesat.setPacID(null);
                    pagesatListOldPagesat = em.merge(pagesatListOldPagesat);
                }
            }
            for (Pagesat pagesatListNewPagesat : pagesatListNew) {
                if (!pagesatListOld.contains(pagesatListNewPagesat)) {
                    Pacienti oldPacIDOfPagesatListNewPagesat = pagesatListNewPagesat.getPacID();
                    pagesatListNewPagesat.setPacID(pacienti);
                    pagesatListNewPagesat = em.merge(pagesatListNewPagesat);
                    if (oldPacIDOfPagesatListNewPagesat != null && !oldPacIDOfPagesatListNewPagesat.equals(pacienti)) {
                        oldPacIDOfPagesatListNewPagesat.getPagesatList().remove(pagesatListNewPagesat);
                        oldPacIDOfPagesatListNewPagesat = em.merge(oldPacIDOfPagesatListNewPagesat);
                    }
                }
            }
            for (Vizitat vizitatListOldVizitat : vizitatListOld) {
                if (!vizitatListNew.contains(vizitatListOldVizitat)) {
                    vizitatListOldVizitat.setPacID(null);
                    vizitatListOldVizitat = em.merge(vizitatListOldVizitat);
                }
            }
            for (Vizitat vizitatListNewVizitat : vizitatListNew) {
                if (!vizitatListOld.contains(vizitatListNewVizitat)) {
                    Pacienti oldPacIDOfVizitatListNewVizitat = vizitatListNewVizitat.getPacID();
                    vizitatListNewVizitat.setPacID(pacienti);
                    vizitatListNewVizitat = em.merge(vizitatListNewVizitat);
                    if (oldPacIDOfVizitatListNewVizitat != null && !oldPacIDOfVizitatListNewVizitat.equals(pacienti)) {
                        oldPacIDOfVizitatListNewVizitat.getVizitatList().remove(vizitatListNewVizitat);
                        oldPacIDOfVizitatListNewVizitat = em.merge(oldPacIDOfVizitatListNewVizitat);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pacienti.getPId();
                if (findPacienti(id) == null) {
                    throw new NonexistentEntityException("The pacienti with id " + id + " no longer exists.");
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
            Pacienti pacienti;
            try {
                pacienti = em.getReference(Pacienti.class, id);
                pacienti.getPId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pacienti with id " + id + " no longer exists.", enfe);
            }
            Klinika klinikaID = pacienti.getKlinikaID();
            if (klinikaID != null) {
                klinikaID.getPacientiList().remove(pacienti);
                klinikaID = em.merge(klinikaID);
            }
            Staff staffID = pacienti.getStaffID();
            if (staffID != null) {
                staffID.getPacientiList().remove(pacienti);
                staffID = em.merge(staffID);
            }
            List<Pagesat> pagesatList = pacienti.getPagesatList();
            for (Pagesat pagesatListPagesat : pagesatList) {
                pagesatListPagesat.setPacID(null);
                pagesatListPagesat = em.merge(pagesatListPagesat);
            }
            List<Vizitat> vizitatList = pacienti.getVizitatList();
            for (Vizitat vizitatListVizitat : vizitatList) {
                vizitatListVizitat.setPacID(null);
                vizitatListVizitat = em.merge(vizitatListVizitat);
            }
            em.remove(pacienti);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pacienti> findPacientiEntities() {
        return findPacientiEntities(true, -1, -1);
    }

    public List<Pacienti> findPacientiEntities(int maxResults, int firstResult) {
        return findPacientiEntities(false, maxResults, firstResult);
    }

    private List<Pacienti> findPacientiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pacienti.class));
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

    public Pacienti findPacienti(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pacienti.class, id);
        } finally {
            em.close();
        }
    }

    public int getPacientiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pacienti> rt = cq.from(Pacienti.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
