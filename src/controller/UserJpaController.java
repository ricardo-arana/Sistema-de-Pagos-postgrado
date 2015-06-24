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
import model.GrupoUsuario;
import model.Estudiante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.User;

/**
 *
 * @author Ricardo
 */
public class UserJpaController implements Serializable {
    private EntityManagerFactory emf = null;
    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) {
        if (user.getEstudianteList() == null) {
            user.setEstudianteList(new ArrayList<Estudiante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoUsuario grupoUsuarioidGrupo = user.getGrupoUsuarioidGrupo();
            if (grupoUsuarioidGrupo != null) {
                grupoUsuarioidGrupo = em.getReference(grupoUsuarioidGrupo.getClass(), grupoUsuarioidGrupo.getIdGrupo());
                user.setGrupoUsuarioidGrupo(grupoUsuarioidGrupo);
            }
            List<Estudiante> attachedEstudianteList = new ArrayList<Estudiante>();
            for (Estudiante estudianteListEstudianteToAttach : user.getEstudianteList()) {
                estudianteListEstudianteToAttach = em.getReference(estudianteListEstudianteToAttach.getClass(), estudianteListEstudianteToAttach.getIdEstudiante());
                attachedEstudianteList.add(estudianteListEstudianteToAttach);
            }
            user.setEstudianteList(attachedEstudianteList);
            em.persist(user);
            if (grupoUsuarioidGrupo != null) {
                grupoUsuarioidGrupo.getUserCollection().add(user);
                grupoUsuarioidGrupo = em.merge(grupoUsuarioidGrupo);
            }
            for (Estudiante estudianteListEstudiante : user.getEstudianteList()) {
                User oldUsuarioRegistroOfEstudianteListEstudiante = estudianteListEstudiante.getUsuarioRegistro();
                estudianteListEstudiante.setUsuarioRegistro(user);
                estudianteListEstudiante = em.merge(estudianteListEstudiante);
                if (oldUsuarioRegistroOfEstudianteListEstudiante != null) {
                    oldUsuarioRegistroOfEstudianteListEstudiante.getEstudianteList().remove(estudianteListEstudiante);
                    oldUsuarioRegistroOfEstudianteListEstudiante = em.merge(oldUsuarioRegistroOfEstudianteListEstudiante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getIdUser());
            GrupoUsuario grupoUsuarioidGrupoOld = persistentUser.getGrupoUsuarioidGrupo();
            GrupoUsuario grupoUsuarioidGrupoNew = user.getGrupoUsuarioidGrupo();
            List<Estudiante> estudianteListOld = persistentUser.getEstudianteList();
            List<Estudiante> estudianteListNew = user.getEstudianteList();
            List<String> illegalOrphanMessages = null;
            for (Estudiante estudianteListOldEstudiante : estudianteListOld) {
                if (!estudianteListNew.contains(estudianteListOldEstudiante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estudiante " + estudianteListOldEstudiante + " since its usuarioRegistro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (grupoUsuarioidGrupoNew != null) {
                grupoUsuarioidGrupoNew = em.getReference(grupoUsuarioidGrupoNew.getClass(), grupoUsuarioidGrupoNew.getIdGrupo());
                user.setGrupoUsuarioidGrupo(grupoUsuarioidGrupoNew);
            }
            List<Estudiante> attachedEstudianteListNew = new ArrayList<Estudiante>();
            for (Estudiante estudianteListNewEstudianteToAttach : estudianteListNew) {
                estudianteListNewEstudianteToAttach = em.getReference(estudianteListNewEstudianteToAttach.getClass(), estudianteListNewEstudianteToAttach.getIdEstudiante());
                attachedEstudianteListNew.add(estudianteListNewEstudianteToAttach);
            }
            estudianteListNew = attachedEstudianteListNew;
            user.setEstudianteList(estudianteListNew);
            user = em.merge(user);
            if (grupoUsuarioidGrupoOld != null && !grupoUsuarioidGrupoOld.equals(grupoUsuarioidGrupoNew)) {
                grupoUsuarioidGrupoOld.getUserCollection().remove(user);
                grupoUsuarioidGrupoOld = em.merge(grupoUsuarioidGrupoOld);
            }
            if (grupoUsuarioidGrupoNew != null && !grupoUsuarioidGrupoNew.equals(grupoUsuarioidGrupoOld)) {
                grupoUsuarioidGrupoNew.getUserCollection().add(user);
                grupoUsuarioidGrupoNew = em.merge(grupoUsuarioidGrupoNew);
            }
            for (Estudiante estudianteListNewEstudiante : estudianteListNew) {
                if (!estudianteListOld.contains(estudianteListNewEstudiante)) {
                    User oldUsuarioRegistroOfEstudianteListNewEstudiante = estudianteListNewEstudiante.getUsuarioRegistro();
                    estudianteListNewEstudiante.setUsuarioRegistro(user);
                    estudianteListNewEstudiante = em.merge(estudianteListNewEstudiante);
                    if (oldUsuarioRegistroOfEstudianteListNewEstudiante != null && !oldUsuarioRegistroOfEstudianteListNewEstudiante.equals(user)) {
                        oldUsuarioRegistroOfEstudianteListNewEstudiante.getEstudianteList().remove(estudianteListNewEstudiante);
                        oldUsuarioRegistroOfEstudianteListNewEstudiante = em.merge(oldUsuarioRegistroOfEstudianteListNewEstudiante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getIdUser();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getIdUser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Estudiante> estudianteListOrphanCheck = user.getEstudianteList();
            for (Estudiante estudianteListOrphanCheckEstudiante : estudianteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Estudiante " + estudianteListOrphanCheckEstudiante + " in its estudianteList field has a non-nullable usuarioRegistro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            GrupoUsuario grupoUsuarioidGrupo = user.getGrupoUsuarioidGrupo();
            if (grupoUsuarioidGrupo != null) {
                grupoUsuarioidGrupo.getUserCollection().remove(user);
                grupoUsuarioidGrupo = em.merge(grupoUsuarioidGrupo);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean Login(User user) {
        EntityManager em = getEntityManager();
        try {
            int n = em.createQuery("SELECT u FROM User u WHERE u.username = :username And u.password = :password")
                    .setParameter("username", user.getUsername())
                    .setParameter("password", user.getPassword())
                    .getResultList().size();
            if (n > 0) {
                return true;
            } else {
                return false;
            }

        } finally {
            em.close();
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
