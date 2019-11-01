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
import dbManagment.Terminet;
import java.util.ArrayList;
import java.util.List;
import dbManagment.Pacienti;
import dbManagment.Pagesat;
import dbManagment.Vizitat;
import dbManagment.Sherbimet;
import dbManagment.Staff;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Egzon
 */
public class StaffJpaController implements Serializable {

    public StaffJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Staff staff) {
        if (staff.getTerminetList() == null) {
            staff.setTerminetList(new ArrayList<Terminet>());
        }
        if (staff.getPacientiList() == null) {
            staff.setPacientiList(new ArrayList<Pacienti>());
        }
        if (staff.getPagesatList() == null) {
            staff.setPagesatList(new ArrayList<Pagesat>());
        }
        if (staff.getVizitatList() == null) {
            staff.setVizitatList(new ArrayList<Vizitat>());
        }
        if (staff.getSherbimetList() == null) {
            staff.setSherbimetList(new ArrayList<Sherbimet>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Klinika klinikaID = staff.getKlinikaID();
            if (klinikaID != null) {
                klinikaID = em.getReference(klinikaID.getClass(), klinikaID.getKId());
                staff.setKlinikaID(klinikaID);
            }
            List<Terminet> attachedTerminetList = new ArrayList<Terminet>();
            for (Terminet terminetListTerminetToAttach : staff.getTerminetList()) {
                terminetListTerminetToAttach = em.getReference(terminetListTerminetToAttach.getClass(), terminetListTerminetToAttach.getTId());
                attachedTerminetList.add(terminetListTerminetToAttach);
            }
            staff.setTerminetList(attachedTerminetList);
            List<Pacienti> attachedPacientiList = new ArrayList<Pacienti>();
            for (Pacienti pacientiListPacientiToAttach : staff.getPacientiList()) {
                pacientiListPacientiToAttach = em.getReference(pacientiListPacientiToAttach.getClass(), pacientiListPacientiToAttach.getPId());
                attachedPacientiList.add(pacientiListPacientiToAttach);
            }
            staff.setPacientiList(attachedPacientiList);
            List<Pagesat> attachedPagesatList = new ArrayList<Pagesat>();
            for (Pagesat pagesatListPagesatToAttach : staff.getPagesatList()) {
                pagesatListPagesatToAttach = em.getReference(pagesatListPagesatToAttach.getClass(), pagesatListPagesatToAttach.getPagesaID());
                attachedPagesatList.add(pagesatListPagesatToAttach);
            }
            staff.setPagesatList(attachedPagesatList);
            List<Vizitat> attachedVizitatList = new ArrayList<Vizitat>();
            for (Vizitat vizitatListVizitatToAttach : staff.getVizitatList()) {
                vizitatListVizitatToAttach = em.getReference(vizitatListVizitatToAttach.getClass(), vizitatListVizitatToAttach.getVId());
                attachedVizitatList.add(vizitatListVizitatToAttach);
            }
            staff.setVizitatList(attachedVizitatList);
            List<Sherbimet> attachedSherbimetList = new ArrayList<Sherbimet>();
            for (Sherbimet sherbimetListSherbimetToAttach : staff.getSherbimetList()) {
                sherbimetListSherbimetToAttach = em.getReference(sherbimetListSherbimetToAttach.getClass(), sherbimetListSherbimetToAttach.getSherbimetID());
                attachedSherbimetList.add(sherbimetListSherbimetToAttach);
            }
            staff.setSherbimetList(attachedSherbimetList);
            em.persist(staff);
            if (klinikaID != null) {
                klinikaID.getStaffList().add(staff);
                klinikaID = em.merge(klinikaID);
            }
            for (Terminet terminetListTerminet : staff.getTerminetList()) {
                Staff oldStaffIDOfTerminetListTerminet = terminetListTerminet.getStaffID();
                terminetListTerminet.setStaffID(staff);
                terminetListTerminet = em.merge(terminetListTerminet);
                if (oldStaffIDOfTerminetListTerminet != null) {
                    oldStaffIDOfTerminetListTerminet.getTerminetList().remove(terminetListTerminet);
                    oldStaffIDOfTerminetListTerminet = em.merge(oldStaffIDOfTerminetListTerminet);
                }
            }
            for (Pacienti pacientiListPacienti : staff.getPacientiList()) {
                Staff oldStaffIDOfPacientiListPacienti = pacientiListPacienti.getStaffID();
                pacientiListPacienti.setStaffID(staff);
                pacientiListPacienti = em.merge(pacientiListPacienti);
                if (oldStaffIDOfPacientiListPacienti != null) {
                    oldStaffIDOfPacientiListPacienti.getPacientiList().remove(pacientiListPacienti);
                    oldStaffIDOfPacientiListPacienti = em.merge(oldStaffIDOfPacientiListPacienti);
                }
            }
            for (Pagesat pagesatListPagesat : staff.getPagesatList()) {
                Staff oldStaffIDOfPagesatListPagesat = pagesatListPagesat.getStaffID();
                pagesatListPagesat.setStaffID(staff);
                pagesatListPagesat = em.merge(pagesatListPagesat);
                if (oldStaffIDOfPagesatListPagesat != null) {
                    oldStaffIDOfPagesatListPagesat.getPagesatList().remove(pagesatListPagesat);
                    oldStaffIDOfPagesatListPagesat = em.merge(oldStaffIDOfPagesatListPagesat);
                }
            }
            for (Vizitat vizitatListVizitat : staff.getVizitatList()) {
                Staff oldStaffIDOfVizitatListVizitat = vizitatListVizitat.getStaffID();
                vizitatListVizitat.setStaffID(staff);
                vizitatListVizitat = em.merge(vizitatListVizitat);
                if (oldStaffIDOfVizitatListVizitat != null) {
                    oldStaffIDOfVizitatListVizitat.getVizitatList().remove(vizitatListVizitat);
                    oldStaffIDOfVizitatListVizitat = em.merge(oldStaffIDOfVizitatListVizitat);
                }
            }
            for (Sherbimet sherbimetListSherbimet : staff.getSherbimetList()) {
                Staff oldStaffIDOfSherbimetListSherbimet = sherbimetListSherbimet.getStaffID();
                sherbimetListSherbimet.setStaffID(staff);
                sherbimetListSherbimet = em.merge(sherbimetListSherbimet);
                if (oldStaffIDOfSherbimetListSherbimet != null) {
                    oldStaffIDOfSherbimetListSherbimet.getSherbimetList().remove(sherbimetListSherbimet);
                    oldStaffIDOfSherbimetListSherbimet = em.merge(oldStaffIDOfSherbimetListSherbimet);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Staff staff) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Staff persistentStaff = em.find(Staff.class, staff.getSId());
            Klinika klinikaIDOld = persistentStaff.getKlinikaID();
            Klinika klinikaIDNew = staff.getKlinikaID();
            List<Terminet> terminetListOld = persistentStaff.getTerminetList();
            List<Terminet> terminetListNew = staff.getTerminetList();
            List<Pacienti> pacientiListOld = persistentStaff.getPacientiList();
            List<Pacienti> pacientiListNew = staff.getPacientiList();
            List<Pagesat> pagesatListOld = persistentStaff.getPagesatList();
            List<Pagesat> pagesatListNew = staff.getPagesatList();
            List<Vizitat> vizitatListOld = persistentStaff.getVizitatList();
            List<Vizitat> vizitatListNew = staff.getVizitatList();
            List<Sherbimet> sherbimetListOld = persistentStaff.getSherbimetList();
            List<Sherbimet> sherbimetListNew = staff.getSherbimetList();
            if (klinikaIDNew != null) {
                klinikaIDNew = em.getReference(klinikaIDNew.getClass(), klinikaIDNew.getKId());
                staff.setKlinikaID(klinikaIDNew);
            }
            List<Terminet> attachedTerminetListNew = new ArrayList<Terminet>();
            for (Terminet terminetListNewTerminetToAttach : terminetListNew) {
                terminetListNewTerminetToAttach = em.getReference(terminetListNewTerminetToAttach.getClass(), terminetListNewTerminetToAttach.getTId());
                attachedTerminetListNew.add(terminetListNewTerminetToAttach);
            }
            terminetListNew = attachedTerminetListNew;
            staff.setTerminetList(terminetListNew);
            List<Pacienti> attachedPacientiListNew = new ArrayList<Pacienti>();
            for (Pacienti pacientiListNewPacientiToAttach : pacientiListNew) {
                pacientiListNewPacientiToAttach = em.getReference(pacientiListNewPacientiToAttach.getClass(), pacientiListNewPacientiToAttach.getPId());
                attachedPacientiListNew.add(pacientiListNewPacientiToAttach);
            }
            pacientiListNew = attachedPacientiListNew;
            staff.setPacientiList(pacientiListNew);
            List<Pagesat> attachedPagesatListNew = new ArrayList<Pagesat>();
            for (Pagesat pagesatListNewPagesatToAttach : pagesatListNew) {
                pagesatListNewPagesatToAttach = em.getReference(pagesatListNewPagesatToAttach.getClass(), pagesatListNewPagesatToAttach.getPagesaID());
                attachedPagesatListNew.add(pagesatListNewPagesatToAttach);
            }
            pagesatListNew = attachedPagesatListNew;
            staff.setPagesatList(pagesatListNew);
            List<Vizitat> attachedVizitatListNew = new ArrayList<Vizitat>();
            for (Vizitat vizitatListNewVizitatToAttach : vizitatListNew) {
                vizitatListNewVizitatToAttach = em.getReference(vizitatListNewVizitatToAttach.getClass(), vizitatListNewVizitatToAttach.getVId());
                attachedVizitatListNew.add(vizitatListNewVizitatToAttach);
            }
            vizitatListNew = attachedVizitatListNew;
            staff.setVizitatList(vizitatListNew);
            List<Sherbimet> attachedSherbimetListNew = new ArrayList<Sherbimet>();
            for (Sherbimet sherbimetListNewSherbimetToAttach : sherbimetListNew) {
                sherbimetListNewSherbimetToAttach = em.getReference(sherbimetListNewSherbimetToAttach.getClass(), sherbimetListNewSherbimetToAttach.getSherbimetID());
                attachedSherbimetListNew.add(sherbimetListNewSherbimetToAttach);
            }
            sherbimetListNew = attachedSherbimetListNew;
            staff.setSherbimetList(sherbimetListNew);
            staff = em.merge(staff);
            if (klinikaIDOld != null && !klinikaIDOld.equals(klinikaIDNew)) {
                klinikaIDOld.getStaffList().remove(staff);
                klinikaIDOld = em.merge(klinikaIDOld);
            }
            if (klinikaIDNew != null && !klinikaIDNew.equals(klinikaIDOld)) {
                klinikaIDNew.getStaffList().add(staff);
                klinikaIDNew = em.merge(klinikaIDNew);
            }
            for (Terminet terminetListOldTerminet : terminetListOld) {
                if (!terminetListNew.contains(terminetListOldTerminet)) {
                    terminetListOldTerminet.setStaffID(null);
                    terminetListOldTerminet = em.merge(terminetListOldTerminet);
                }
            }
            for (Terminet terminetListNewTerminet : terminetListNew) {
                if (!terminetListOld.contains(terminetListNewTerminet)) {
                    Staff oldStaffIDOfTerminetListNewTerminet = terminetListNewTerminet.getStaffID();
                    terminetListNewTerminet.setStaffID(staff);
                    terminetListNewTerminet = em.merge(terminetListNewTerminet);
                    if (oldStaffIDOfTerminetListNewTerminet != null && !oldStaffIDOfTerminetListNewTerminet.equals(staff)) {
                        oldStaffIDOfTerminetListNewTerminet.getTerminetList().remove(terminetListNewTerminet);
                        oldStaffIDOfTerminetListNewTerminet = em.merge(oldStaffIDOfTerminetListNewTerminet);
                    }
                }
            }
            for (Pacienti pacientiListOldPacienti : pacientiListOld) {
                if (!pacientiListNew.contains(pacientiListOldPacienti)) {
                    pacientiListOldPacienti.setStaffID(null);
                    pacientiListOldPacienti = em.merge(pacientiListOldPacienti);
                }
            }
            for (Pacienti pacientiListNewPacienti : pacientiListNew) {
                if (!pacientiListOld.contains(pacientiListNewPacienti)) {
                    Staff oldStaffIDOfPacientiListNewPacienti = pacientiListNewPacienti.getStaffID();
                    pacientiListNewPacienti.setStaffID(staff);
                    pacientiListNewPacienti = em.merge(pacientiListNewPacienti);
                    if (oldStaffIDOfPacientiListNewPacienti != null && !oldStaffIDOfPacientiListNewPacienti.equals(staff)) {
                        oldStaffIDOfPacientiListNewPacienti.getPacientiList().remove(pacientiListNewPacienti);
                        oldStaffIDOfPacientiListNewPacienti = em.merge(oldStaffIDOfPacientiListNewPacienti);
                    }
                }
            }
            for (Pagesat pagesatListOldPagesat : pagesatListOld) {
                if (!pagesatListNew.contains(pagesatListOldPagesat)) {
                    pagesatListOldPagesat.setStaffID(null);
                    pagesatListOldPagesat = em.merge(pagesatListOldPagesat);
                }
            }
            for (Pagesat pagesatListNewPagesat : pagesatListNew) {
                if (!pagesatListOld.contains(pagesatListNewPagesat)) {
                    Staff oldStaffIDOfPagesatListNewPagesat = pagesatListNewPagesat.getStaffID();
                    pagesatListNewPagesat.setStaffID(staff);
                    pagesatListNewPagesat = em.merge(pagesatListNewPagesat);
                    if (oldStaffIDOfPagesatListNewPagesat != null && !oldStaffIDOfPagesatListNewPagesat.equals(staff)) {
                        oldStaffIDOfPagesatListNewPagesat.getPagesatList().remove(pagesatListNewPagesat);
                        oldStaffIDOfPagesatListNewPagesat = em.merge(oldStaffIDOfPagesatListNewPagesat);
                    }
                }
            }
            for (Vizitat vizitatListOldVizitat : vizitatListOld) {
                if (!vizitatListNew.contains(vizitatListOldVizitat)) {
                    vizitatListOldVizitat.setStaffID(null);
                    vizitatListOldVizitat = em.merge(vizitatListOldVizitat);
                }
            }
            for (Vizitat vizitatListNewVizitat : vizitatListNew) {
                if (!vizitatListOld.contains(vizitatListNewVizitat)) {
                    Staff oldStaffIDOfVizitatListNewVizitat = vizitatListNewVizitat.getStaffID();
                    vizitatListNewVizitat.setStaffID(staff);
                    vizitatListNewVizitat = em.merge(vizitatListNewVizitat);
                    if (oldStaffIDOfVizitatListNewVizitat != null && !oldStaffIDOfVizitatListNewVizitat.equals(staff)) {
                        oldStaffIDOfVizitatListNewVizitat.getVizitatList().remove(vizitatListNewVizitat);
                        oldStaffIDOfVizitatListNewVizitat = em.merge(oldStaffIDOfVizitatListNewVizitat);
                    }
                }
            }
            for (Sherbimet sherbimetListOldSherbimet : sherbimetListOld) {
                if (!sherbimetListNew.contains(sherbimetListOldSherbimet)) {
                    sherbimetListOldSherbimet.setStaffID(null);
                    sherbimetListOldSherbimet = em.merge(sherbimetListOldSherbimet);
                }
            }
            for (Sherbimet sherbimetListNewSherbimet : sherbimetListNew) {
                if (!sherbimetListOld.contains(sherbimetListNewSherbimet)) {
                    Staff oldStaffIDOfSherbimetListNewSherbimet = sherbimetListNewSherbimet.getStaffID();
                    sherbimetListNewSherbimet.setStaffID(staff);
                    sherbimetListNewSherbimet = em.merge(sherbimetListNewSherbimet);
                    if (oldStaffIDOfSherbimetListNewSherbimet != null && !oldStaffIDOfSherbimetListNewSherbimet.equals(staff)) {
                        oldStaffIDOfSherbimetListNewSherbimet.getSherbimetList().remove(sherbimetListNewSherbimet);
                        oldStaffIDOfSherbimetListNewSherbimet = em.merge(oldStaffIDOfSherbimetListNewSherbimet);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = staff.getSId();
                if (findStaff(id) == null) {
                    throw new NonexistentEntityException("The staff with id " + id + " no longer exists.");
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
            Staff staff;
            try {
                staff = em.getReference(Staff.class, id);
                staff.getSId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The staff with id " + id + " no longer exists.", enfe);
            }
            Klinika klinikaID = staff.getKlinikaID();
            if (klinikaID != null) {
                klinikaID.getStaffList().remove(staff);
                klinikaID = em.merge(klinikaID);
            }
            List<Terminet> terminetList = staff.getTerminetList();
            for (Terminet terminetListTerminet : terminetList) {
                terminetListTerminet.setStaffID(null);
                terminetListTerminet = em.merge(terminetListTerminet);
            }
            List<Pacienti> pacientiList = staff.getPacientiList();
            for (Pacienti pacientiListPacienti : pacientiList) {
                pacientiListPacienti.setStaffID(null);
                pacientiListPacienti = em.merge(pacientiListPacienti);
            }
            List<Pagesat> pagesatList = staff.getPagesatList();
            for (Pagesat pagesatListPagesat : pagesatList) {
                pagesatListPagesat.setStaffID(null);
                pagesatListPagesat = em.merge(pagesatListPagesat);
            }
            List<Vizitat> vizitatList = staff.getVizitatList();
            for (Vizitat vizitatListVizitat : vizitatList) {
                vizitatListVizitat.setStaffID(null);
                vizitatListVizitat = em.merge(vizitatListVizitat);
            }
            List<Sherbimet> sherbimetList = staff.getSherbimetList();
            for (Sherbimet sherbimetListSherbimet : sherbimetList) {
                sherbimetListSherbimet.setStaffID(null);
                sherbimetListSherbimet = em.merge(sherbimetListSherbimet);
            }
            em.remove(staff);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Staff> findStaffEntities() {
        return findStaffEntities(true, -1, -1);
    }

    public List<Staff> findStaffEntities(int maxResults, int firstResult) {
        return findStaffEntities(false, maxResults, firstResult);
    }

    private List<Staff> findStaffEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Staff.class));
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

    public Staff findStaff(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Staff.class, id);
        } finally {
            em.close();
        }
    }

    public int getStaffCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Staff> rt = cq.from(Staff.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
