package no.hvl.dat107.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Avdeling;

public class AvdelingDAO {

	private EntityManagerFactory emf;
	
	public AvdelingDAO() {
		emf = Persistence.createEntityManagerFactory("AnsattProsjektPU");
	}
	
	public Avdeling finnAvdelingMedId(int id) {
		 EntityManager em = emf.createEntityManager();

	        Avdeling Avdeling = null;
	        try {
	            Avdeling = em.find(Avdeling.class, id);
	        } finally {
	            em.close();
	        }
	        return Avdeling;
	}
	
	public List <Avdeling> finnAlleAvdelinger() {
		
		 EntityManager em = emf.createEntityManager();

	     List <Avdeling> avdelinger = null;
	     
	     try {
	    	 TypedQuery<Avdeling> tq = em.createQuery("SELECT avd FROM Avdeling avd", Avdeling.class);
	    	 avdelinger =tq.getResultList();
	     } finally {
	    	 em.close();
	     }
		return avdelinger;
		
	}
	
	public Ansatt finnSjef(int avdId) {
		EntityManager em = emf.createEntityManager();
		Ansatt sjef=null;
		try {
			Avdeling avd = em.find(Avdeling.class, avdId);
			sjef=avd.getAnsatt();
		} finally {
		em.close();	
		}
		return sjef;
	}
	
	public void utlysAlleAnsatte(int id) {
		EntityManager em = emf.createEntityManager();
		List<Ansatt> ansatte= null;
		try {
			String queryString= "SELECT a FROM Ansatt a WHERE a.avdeling_id= :id";
			TypedQuery<Ansatt> query = em.createQuery(queryString, Ansatt.class);
			query.setParameter("id", id);
			
			Ansatt sjef = finnSjef(id);
			
			ansatte = query.getResultList();
			
			for(int i=0; i < ansatte.size(); i++) {
				if(ansatte.get(i).getId() != sjef.getId()) {
					ansatte.get(i).skrivUt(" ");
				} else {
					System.out.print(" SJEF: ");
					ansatte.get(i).skrivUt(" ");
				}
			}
			
		
			
			
		} finally {
			em.close();
		}
	}
	
	public void lagNyAvdeling(int id, String navn, Ansatt ansatt) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();			
			
			Avdeling avdGammel = em.find(Avdeling.class, ansatt.getAvdeling_id());
				if(!avdGammel.getAnsatt().equals(ansatt)) {
					
					Avdeling avd= new Avdeling(id, navn,ansatt);
					em.persist(avd);
					avd.skrivUt(" ");
				} else {
					System.out.println("Sjef har allerede en avdeling");
				}
			
			
			tx.commit();
		} finally {
			em.close();
		}
		
		
	}
	
	public void oppdaterAvdeling (Ansatt ansatt, int id) {
		 EntityManager em = emf.createEntityManager();
		 EntityTransaction tx = em.getTransaction();

		 try {
			 tx.begin();
				Avdeling avdGammel = em.find(Avdeling.class, ansatt.getAvdeling_id());
				if(!avdGammel.getAnsatt().equals(ansatt)) {
				 em.merge(ansatt).setAvdeling_id(id);
				 Avdeling avdeling = finnAvdelingMedId(id);
				 System.out.println("Ny Endringer på ansatt :" + ansatt.getFornavn());
				 System.out.print("Jobber nå i avdeling: ");
				 avdeling.skrivUt(" ");
			 } else {
				 System.out.println("Ansatt er sjef og kan ikke bytte avdeling :( ");
			 }
			 tx.commit();
			 
		 } finally {
			 em.close();
		 }
	}
	

	
}
