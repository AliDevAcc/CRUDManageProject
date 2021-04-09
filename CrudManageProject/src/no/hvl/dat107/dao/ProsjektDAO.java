package no.hvl.dat107.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Prosjekt;

public class ProsjektDAO {

    private EntityManagerFactory emf;

    public ProsjektDAO() {
        emf = Persistence.createEntityManagerFactory("AnsattProsjektPU");
    }

    public Prosjekt finnProsjektMedId(int id) {

        EntityManager em = emf.createEntityManager();

        Prosjekt prosjekt = null;
        try {
            prosjekt = em.find(Prosjekt.class, id);
        } finally {
            em.close();
        }
        return prosjekt;
    }
    
    public void leggTilProsjekt(Prosjekt p) {
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx= em.getTransaction();
    	try {
    		tx.begin();
    		em.persist(p);
    		tx.commit();
    	} finally {
    		em.close();
    	}
    }
}
