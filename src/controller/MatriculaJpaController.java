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
import model.Ciclo;
import model.Matricula;

/**
 *
 * @author Ricardo
 */
public class MatriculaJpaController implements Serializable {

    public MatriculaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Matricula matricula) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante estudianteidEstudiante = matricula.getEstudianteidEstudiante();
            if (estudianteidEstudiante != null) {
                estudianteidEstudiante = em.getReference(estudianteidEstudiante.getClass(), estudianteidEstudiante.getIdEstudiante());
                matricula.setEstudianteidEstudiante(estudianteidEstudiante);
            }
            Ciclo cicloidCiclo = matricula.getCicloidCiclo();
            if (cicloidCiclo != null) {
                cicloidCiclo = em.getReference(cicloidCiclo.getClass(), cicloidCiclo.getIdCiclo());
                matricula.setCicloidCiclo(cicloidCiclo);
            }
            em.persist(matricula);
            if (estudianteidEstudiante != null) {
                estudianteidEstudiante.getMatriculaList().add(matricula);
                estudianteidEstudiante = em.merge(estudianteidEstudiante);
            }
            if (cicloidCiclo != null) {
                cicloidCiclo.getMatriculaList().add(matricula);
                cicloidCiclo = em.merge(cicloidCiclo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Matricula matricula) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Matricula persistentMatricula = em.find(Matricula.class, matricula.getIdmatricula());
            Estudiante estudianteidEstudianteOld = persistentMatricula.getEstudianteidEstudiante();
            Estudiante estudianteidEstudianteNew = matricula.getEstudianteidEstudiante();
            Ciclo cicloidCicloOld = persistentMatricula.getCicloidCiclo();
            Ciclo cicloidCicloNew = matricula.getCicloidCiclo();
            if (estudianteidEstudianteNew != null) {
                estudianteidEstudianteNew = em.getReference(estudianteidEstudianteNew.getClass(), estudianteidEstudianteNew.getIdEstudiante());
                matricula.setEstudianteidEstudiante(estudianteidEstudianteNew);
            }
            if (cicloidCicloNew != null) {
                cicloidCicloNew = em.getReference(cicloidCicloNew.getClass(), cicloidCicloNew.getIdCiclo());
                matricula.setCicloidCiclo(cicloidCicloNew);
            }
            matricula = em.merge(matricula);
            if (estudianteidEstudianteOld != null && !estudianteidEstudianteOld.equals(estudianteidEstudianteNew)) {
                estudianteidEstudianteOld.getMatriculaList().remove(matricula);
                estudianteidEstudianteOld = em.merge(estudianteidEstudianteOld);
            }
            if (estudianteidEstudianteNew != null && !estudianteidEstudianteNew.equals(estudianteidEstudianteOld)) {
                estudianteidEstudianteNew.getMatriculaList().add(matricula);
                estudianteidEstudianteNew = em.merge(estudianteidEstudianteNew);
            }
            if (cicloidCicloOld != null && !cicloidCicloOld.equals(cicloidCicloNew)) {
                cicloidCicloOld.getMatriculaList().remove(matricula);
                cicloidCicloOld = em.merge(cicloidCicloOld);
            }
            if (cicloidCicloNew != null && !cicloidCicloNew.equals(cicloidCicloOld)) {
                cicloidCicloNew.getMatriculaList().add(matricula);
                cicloidCicloNew = em.merge(cicloidCicloNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = matricula.getIdmatricula();
                if (findMatricula(id) == null) {
                    throw new NonexistentEntityException("The matricula with id " + id + " no longer exists.");
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
            Matricula matricula;
            try {
                matricula = em.getReference(Matricula.class, id);
                matricula.getIdmatricula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The matricula with id " + id + " no longer exists.", enfe);
            }
            Estudiante estudianteidEstudiante = matricula.getEstudianteidEstudiante();
            if (estudianteidEstudiante != null) {
                estudianteidEstudiante.getMatriculaList().remove(matricula);
                estudianteidEstudiante = em.merge(estudianteidEstudiante);
            }
            Ciclo cicloidCiclo = matricula.getCicloidCiclo();
            if (cicloidCiclo != null) {
                cicloidCiclo.getMatriculaList().remove(matricula);
                cicloidCiclo = em.merge(cicloidCiclo);
            }
            em.remove(matricula);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public List<Matricula> findMatriculaEntities() {
        return findMatriculaEntities(true, -1, -1);
    }
    
    public List<Matricula> findByEstudiante(Estudiante e) {
        EntityManager em = getEntityManager();
        return em.createNamedQuery("Matricula.findByEstudiante")
                .setParameter("estudianteidEstudiante", e)
                .getResultList();
    }

    public List<Matricula> findMatriculaEntities(int maxResults, int firstResult) {
        return findMatriculaEntities(false, maxResults, firstResult);
    }

    private List<Matricula> findMatriculaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Matricula.class));
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

    public Matricula findMatricula(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Matricula.class, id);
        } finally {
            em.close();
        }
    }

    public int getMatriculaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Matricula> rt = cq.from(Matricula.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
