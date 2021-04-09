package no.hvl.dat107.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Prosjekt;
import no.hvl.dat107.entity.Prosjektdeltagelse;

public class AnsattDAO {

    private EntityManagerFactory emf;

    public AnsattDAO() {
        emf = Persistence.createEntityManagerFactory("AnsattProsjektPU");
    }
    
    public Ansatt finnAnsattMedId(int id) {

        EntityManager em = emf.createEntityManager();

        Ansatt ansatt = null;
        try {
            ansatt = em.find(Ansatt.class, id);
        } finally {
            em.close();
        }
        return ansatt;
    }

    public Ansatt finnAnsattMedInitialer(String id) {

        EntityManager em = emf.createEntityManager();

       Ansatt ansatt = null;
        try {
        	String queryString="SELECT ab FROM Ansatt ab WHERE ab.initialer =:id";
            TypedQuery <Ansatt> a = em.createQuery(queryString, Ansatt.class);
            a.setParameter("id", id);
            ansatt=a.getSingleResult();
        } finally {
            em.close();
        }
        return ansatt;
    }
    
    public List<Ansatt> finnAnsatte() {

        EntityManager em = emf.createEntityManager();
        
        String queryString = "SELECT a from Ansatt a";
        
        List <Ansatt> ansatte = null;
        try {
           TypedQuery<Ansatt> query = em.createQuery(queryString, Ansatt.class);
           ansatte=query.getResultList();
        } finally {
            em.close();
        }
        return ansatte;
    }
    
    public void oppdaterAnsattStilling(int id, String stilling) {
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx= em.getTransaction();
    	Ansatt a = null;
    	try {
    		tx.begin();
    		a=em.find(Ansatt.class, id);
    		a.setStilling(stilling);
    		em.merge(a);
    		tx.commit();
    	} finally {
    		em.close();
    	}
    }

    public void leggTilAnsatt(Ansatt a) {
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx= em.getTransaction();
    	try {
    		tx.begin();
    		em.persist(a);
    		a.skrivUt(" ");
    		tx.commit();
    	} finally {
    		em.close();
    	}
    }

    
    public void registrerProsjektdeltagelse(Ansatt a, Prosjekt p) {
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            
            Prosjektdeltagelse pd = new Prosjektdeltagelse(a, p, 0);
            
            em.merge(a);
            em.merge(p);
            
            em.persist(pd);
            System.out.println("La til Ansatt: " + a.getFornavn() + 
            		", i prosjekt: " + p.getNavn());
            tx.commit();
        } catch (Throwable e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
        
    }

    public void slettProsjektdeltagelse(Ansatt a, Prosjekt p, int id) {
    	
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Prosjektdeltagelse pd = null;
        try {
            tx.begin();
            
            pd = em.find(Prosjektdeltagelse.class, id);
            em.merge(p).fjernProsjektdeltagelse(pd);
            em.merge(a).fjernProsjektdeltagelse(pd);
            em.remove(pd);
            System.out.println("Fjernet Ansatt: " + a.getFornavn() + 
            		", fra prosjekt: " + p.getNavn());
            tx.commit();
        } catch (Throwable e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }
    
    public void leggTilTimer(int ansattId, int prosjektId, int timer) {
    	
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Prosjektdeltagelse pd = null;
        try {
            tx.begin();
            
            pd =finnProsjektdeltagelse(ansattId,prosjektId);
            System.out.println("La til " + timer + " timer, totalt timer: " + (pd.getTimer()+timer) + ", timer for ansatt: " + pd.getAnsatt().getFornavn()
            		+ ", i prosjekt: " + pd.getProsjekt().getNavn());
            em.merge(pd).addTimer(timer);
          
            
            tx.commit();
        } catch (Throwable e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }
    

    private Prosjektdeltagelse finnProsjektdeltagelse(int ansattId, int prosjektId) {
        
        String queryString = "SELECT pd FROM Prosjektdeltagelse pd " 
                + "WHERE pd.ansatt.id = :ansattId AND pd.prosjekt.id = :prosjektId";

        EntityManager em = emf.createEntityManager();

        Prosjektdeltagelse pd = null;
        try {
            TypedQuery<Prosjektdeltagelse> query 
                    = em.createQuery(queryString, Prosjektdeltagelse.class);
            query.setParameter("ansattId", ansattId);
            query.setParameter("prosjektId", prosjektId);
            pd = query.getSingleResult();
            
        } catch (NoResultException e) {
            // e.printStackTrace();
        } finally {
            em.close();
        }
        return pd;
    }
    
}
