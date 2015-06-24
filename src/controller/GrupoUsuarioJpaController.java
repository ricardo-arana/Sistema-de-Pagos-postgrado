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
import model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.GrupoUsuario;

/**
 *
 * @author Ricardo
 */
public class GrupoUsuarioJpaController implements Serializable {

    public GrupoUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GrupoUsuario grupoUsuario) {
        if (grupoUsuario.getUserCollection() == null) {
            grupoUsuario.setUserCollection(new ArrayList<User>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<User> attachedUserCollection = new ArrayList<User>();
            for (User userCollectionUserToAttach : grupoUsuario.getUserCollection()) {
                userCollectionUserToAttach = em.getReference(userCollectionUserToAttach.getClass(), userCollectionUserToAttach.getIdUser());
                attachedUserCollection.add(userCollectionUserToAttach);
            }
            grupoUsuario.setUserCollection(attachedUserCollection);
            em.persist(grupoUsuario);
            for (User userCollectionUser : grupoUsuario.getUserCollection()) {
                GrupoUsuario oldGrupoUsuarioidGrupoOfUserCollectionUser = userCollectionUser.getGrupoUsuarioidGrupo();
                userCollectionUser.setGrupoUsuarioidGrupo(grupoUsuario);
                userCollectionUser = em.merge(userCollectionUser);
                if (oldGrupoUsuarioidGrupoOfUserCollectionUser != null) {
                    oldGrupoUsuarioidGrupoOfUserCollectionUser.getUserCollection().remove(userCollectionUser);
                    oldGrupoUsuarioidGrupoOfUserCollectionUser = em.merge(oldGrupoUsuarioidGrupoOfUserCollectionUser);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GrupoUsuario grupoUsuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoUsuario persistentGrupoUsuario = em.find(GrupoUsuario.class, grupoUsuario.getIdGrupo());
            Collection<User> userCollectionOld = persistentGrupoUsuario.getUserCollection();
            Collection<User> userCollectionNew = grupoUsuario.getUserCollection();
            List<String> illegalOrphanMessages = null;
            for (User userCollectionOldUser : userCollectionOld) {
                if (!userCollectionNew.contains(userCollectionOldUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain User " + userCollectionOldUser + " since its grupoUsuarioidGrupo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<User> attachedUserCollectionNew = new ArrayList<User>();
            for (User userCollectionNewUserToAttach : userCollectionNew) {
                userCollectionNewUserToAttach = em.getReference(userCollectionNewUserToAttach.getClass(), userCollectionNewUserToAttach.getIdUser());
                attachedUserCollectionNew.add(userCollectionNewUserToAttach);
            }
            userCollectionNew = attachedUserCollectionNew;
            grupoUsuario.setUserCollection(userCollectionNew);
            grupoUsuario = em.merge(grupoUsuario);
            for (User userCollectionNewUser : userCollectionNew) {
                if (!userCollectionOld.contains(userCollectionNewUser)) {
                    GrupoUsuario oldGrupoUsuarioidGrupoOfUserCollectionNewUser = userCollectionNewUser.getGrupoUsuarioidGrupo();
                    userCollectionNewUser.setGrupoUsuarioidGrupo(grupoUsuario);
                    userCollectionNewUser = em.merge(userCollectionNewUser);
                    if (oldGrupoUsuarioidGrupoOfUserCollectionNewUser != null && !oldGrupoUsuarioidGrupoOfUserCollectionNewUser.equals(grupoUsuario)) {
                        oldGrupoUsuarioidGrupoOfUserCollectionNewUser.getUserCollection().remove(userCollectionNewUser);
                        oldGrupoUsuarioidGrupoOfUserCollectionNewUser = em.merge(oldGrupoUsuarioidGrupoOfUserCollectionNewUser);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grupoUsuario.getIdGrupo();
                if (findGrupoUsuario(id) == null) {
                    throw new NonexistentEntityException("The grupoUsuario with id " + id + " no longer exists.");
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
            GrupoUsuario grupoUsuario;
            try {
                grupoUsuario = em.getReference(GrupoUsuario.class, id);
                grupoUsuario.getIdGrupo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupoUsuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<User> userCollectionOrphanCheck = grupoUsuario.getUserCollection();
            for (User userCollectionOrphanCheckUser : userCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GrupoUsuario (" + grupoUsuario + ") cannot be destroyed since the User " + userCollectionOrphanCheckUser + " in its userCollection field has a non-nullable grupoUsuarioidGrupo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(grupoUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GrupoUsuario> findGrupoUsuarioEntities() {
        return findGrupoUsuarioEntities(true, -1, -1);
    }

    public List<GrupoUsuario> findGrupoUsuarioEntities(int maxResults, int firstResult) {
        return findGrupoUsuarioEntities(false, maxResults, firstResult);
    }

    private List<GrupoUsuario> findGrupoUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GrupoUsuario.class));
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

    public GrupoUsuario findGrupoUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GrupoUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GrupoUsuario> rt = cq.from(GrupoUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
