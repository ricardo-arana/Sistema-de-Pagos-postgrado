/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Estudiante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Maestria;

/**
 *
 * @author Ricardo
 */
public class MaestriaJpaController implements Serializable {

    public MaestriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Maestria maestria) {
        if (maestria.getEstudianteList() == null) {
            maestria.setEstudianteList(new ArrayList<Estudiante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Estudiante> attachedEstudianteList = new ArrayList<Estudiante>();
            for (Estudiante estudianteListEstudianteToAttach : maestria.getEstudianteList()) {
                estudianteListEstudianteToAttach = em.getReference(estudianteListEstudianteToAttach.getClass(), estudianteListEstudianteToAttach.getIdEstudiante());
                attachedEstudianteList.add(estudianteListEstudianteToAttach);
            }
            maestria.setEstudianteList(attachedEstudianteList);
            em.persist(maestria);
            for (Estudiante estudianteListEstudiante : maestria.getEstudianteList()) {
                Maestria oldMaestriaOfEstudianteListEstudiante = estudianteListEstudiante.getMaestria();
                estudianteListEstudiante.setMaestria(maestria);
                estudianteListEstudiante = em.merge(estudianteListEstudiante);
                if (oldMaestriaOfEstudianteListEstudiante != null) {
                    oldMaestriaOfEstudianteListEstudiante.getEstudianteList().remove(estudianteListEstudiante);
                    oldMaestriaOfEstudianteListEstudiante = em.merge(oldMaestriaOfEstudianteListEstudiante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Maestria maestria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestria persistentMaestria = em.find(Maestria.class, maestria.getIdMaestria());
            List<Estudiante> estudianteListOld = persistentMaestria.getEstudianteList();
            List<Estudiante> estudianteListNew = maestria.getEstudianteList();
            List<String> illegalOrphanMessages = null;
            for (Estudiante estudianteListOldEstudiante : estudianteListOld) {
                if (!estudianteListNew.contains(estudianteListOldEstudiante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estudiante " + estudianteListOldEstudiante + " since its maestria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Estudiante> attachedEstudianteListNew = new ArrayList<Estudiante>();
            for (Estudiante estudianteListNewEstudianteToAttach : estudianteListNew) {
                estudianteListNewEstudianteToAttach = em.getReference(estudianteListNewEstudianteToAttach.getClass(), estudianteListNewEstudianteToAttach.getIdEstudiante());
                attachedEstudianteListNew.add(estudianteListNewEstudianteToAttach);
            }
            estudianteListNew = attachedEstudianteListNew;
            maestria.setEstudianteList(estudianteListNew);
            maestria = em.merge(maestria);
            for (Estudiante estudianteListNewEstudiante : estudianteListNew) {
                if (!estudianteListOld.contains(estudianteListNewEstudiante)) {
                    Maestria oldMaestriaOfEstudianteListNewEstudiante = estudianteListNewEstudiante.getMaestria();
                    estudianteListNewEstudiante.setMaestria(maestria);
                    estudianteListNewEstudiante = em.merge(estudianteListNewEstudiante);
                    if (oldMaestriaOfEstudianteListNewEstudiante != null && !oldMaestriaOfEstudianteListNewEstudiante.equals(maestria)) {
                        oldMaestriaOfEstudianteListNewEstudiante.getEstudianteList().remove(estudianteListNewEstudiante);
                        oldMaestriaOfEstudianteListNewEstudiante = em.merge(oldMaestriaOfEstudianteListNewEstudiante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = maestria.getIdMaestria();
                if (findMaestria(id) == null) {
                    throw new NonexistentEntityException("The maestria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestria maestria;
            try {
                maestria = em.getReference(Maestria.class, id);
                maestria.getIdMaestria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The maestria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Estudiante> estudianteListOrphanCheck = maestria.getEstudianteList();
            for (Estudiante estudianteListOrphanCheckEstudiante : estudianteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Maestria (" + maestria + ") cannot be destroyed since the Estudiante " + estudianteListOrphanCheckEstudiante + " in its estudianteList field has a non-nullable maestria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(maestria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Maestria> findMaestriaEntities() {
        return findMaestriaEntities(true, -1, -1);
    }

    public List<Maestria> findMaestriaEntities(int maxResults, int firstResult) {
        return findMaestriaEntities(false, maxResults, firstResult);
    }

    private List<Maestria> findMaestriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Maestria.class));
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

    public Maestria findMaestria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Maestria.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaestriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Maestria> rt = cq.from(Maestria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
