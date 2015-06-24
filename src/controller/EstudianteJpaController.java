/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Estudiante;
import model.User;
import model.Maestria;

/**
 *
 * @author Ricardo
 */
public class EstudianteJpaController implements Serializable {

    public EstudianteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudiante estudiante) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User usuarioRegistro = estudiante.getUsuarioRegistro();
            if (usuarioRegistro != null) {
                usuarioRegistro = em.getReference(usuarioRegistro.getClass(), usuarioRegistro.getIdUser());
                estudiante.setUsuarioRegistro(usuarioRegistro);
            }
            Maestria maestria = estudiante.getMaestria();
            if (maestria != null) {
                maestria = em.getReference(maestria.getClass(), maestria.getIdMaestria());
                estudiante.setMaestria(maestria);
            }
            em.persist(estudiante);
            if (usuarioRegistro != null) {
                usuarioRegistro.getEstudianteList().add(estudiante);
                usuarioRegistro = em.merge(usuarioRegistro);
            }
            if (maestria != null) {
                maestria.getEstudianteList().add(estudiante);
                maestria = em.merge(maestria);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estudiante estudiante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante persistentEstudiante = em.find(Estudiante.class, estudiante.getIdEstudiante());
            User usuarioRegistroOld = persistentEstudiante.getUsuarioRegistro();
            User usuarioRegistroNew = estudiante.getUsuarioRegistro();
            Maestria maestriaOld = persistentEstudiante.getMaestria();
            Maestria maestriaNew = estudiante.getMaestria();
            if (usuarioRegistroNew != null) {
                usuarioRegistroNew = em.getReference(usuarioRegistroNew.getClass(), usuarioRegistroNew.getIdUser());
                estudiante.setUsuarioRegistro(usuarioRegistroNew);
            }
            if (maestriaNew != null) {
                maestriaNew = em.getReference(maestriaNew.getClass(), maestriaNew.getIdMaestria());
                estudiante.setMaestria(maestriaNew);
            }
            estudiante = em.merge(estudiante);
            if (usuarioRegistroOld != null && !usuarioRegistroOld.equals(usuarioRegistroNew)) {
                usuarioRegistroOld.getEstudianteList().remove(estudiante);
                usuarioRegistroOld = em.merge(usuarioRegistroOld);
            }
            if (usuarioRegistroNew != null && !usuarioRegistroNew.equals(usuarioRegistroOld)) {
                usuarioRegistroNew.getEstudianteList().add(estudiante);
                usuarioRegistroNew = em.merge(usuarioRegistroNew);
            }
            if (maestriaOld != null && !maestriaOld.equals(maestriaNew)) {
                maestriaOld.getEstudianteList().remove(estudiante);
                maestriaOld = em.merge(maestriaOld);
            }
            if (maestriaNew != null && !maestriaNew.equals(maestriaOld)) {
                maestriaNew.getEstudianteList().add(estudiante);
                maestriaNew = em.merge(maestriaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estudiante.getIdEstudiante();
                if (findEstudiante(id) == null) {
                    throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.");
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
            Estudiante estudiante;
            try {
                estudiante = em.getReference(Estudiante.class, id);
                estudiante.getIdEstudiante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.", enfe);
            }
            User usuarioRegistro = estudiante.getUsuarioRegistro();
            if (usuarioRegistro != null) {
                usuarioRegistro.getEstudianteList().remove(estudiante);
                usuarioRegistro = em.merge(usuarioRegistro);
            }
            Maestria maestria = estudiante.getMaestria();
            if (maestria != null) {
                maestria.getEstudianteList().remove(estudiante);
                maestria = em.merge(maestria);
            }
            em.remove(estudiante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estudiante> findEstudianteEntities() {
        return findEstudianteEntities(true, -1, -1);
    }

    public List<Estudiante> findEstudianteEntities(int maxResults, int firstResult) {
        return findEstudianteEntities(false, maxResults, firstResult);
    }

    private List<Estudiante> findEstudianteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudiante.class));
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
    
    public List<Estudiante> buscarPorNombre(String nombre, String Apellido){
        EntityManager em = getEntityManager();
        List<Estudiante> est = em.createNamedQuery("Estudiante.findByNombre")
                .setParameter("nombre", nombre)
                .setParameter("apellido", Apellido)
                .getResultList();
        
        
        return est;
    }

    public Estudiante findEstudiante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudiante.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudianteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudiante> rt = cq.from(Estudiante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
