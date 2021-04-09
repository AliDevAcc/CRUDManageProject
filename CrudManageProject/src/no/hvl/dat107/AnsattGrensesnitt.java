package no.hvl.dat107;

import static javax.swing.JOptionPane.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JOptionPane;

import no.hvl.dat107.dao.AnsattDAO;
import no.hvl.dat107.dao.AvdelingDAO;
import no.hvl.dat107.dao.ProsjektDAO;
import no.hvl.dat107.entity.Ansatt;
public class AnsattGrensesnitt {
	private static String[] ALTERNATIVER = {"Legg inn nytt Ansatt", "Finn ansatt med Id",
			"Finn ansatt med initialer","Utlisting av alle ansatter", "Oppdater stilling for en ansatt "};
	private AvdelingDAO avdelingDAO= new AvdelingDAO();
	private AnsattDAO ansattDAO=new AnsattDAO();
	private ProsjektDAO prosjektDAO=new ProsjektDAO();
	
	public int lesValg( ) {
		int valg = JOptionPane.showOptionDialog(null,"Gjør et valg","BedriftInfo",JOptionPane.DEFAULT_OPTION,
		JOptionPane.PLAIN_MESSAGE,null,ALTERNATIVER,ALTERNATIVER[0]);
		return valg;
	}
	
	public void meny() {
		boolean fortsett = true;
		//Løkken går til Avslutt er valgt:
		while(fortsett) {
			int valg = lesValg();
			switch(valg) {
				case 0 : nyAnsatt();
					break;
				case 1 : finnAnsattId();
					break;
				case 2 : finnAnsattInitialer();
					break;
				case 3 : utlysAlleAnsatte();
				break;
				case 4 :oppdaterAnsatt();
					break;
				default : fortsett = false;
			}
		} //løkke		 
	} //metode
	
	
	private void oppdaterAnsatt() {
		String idInput = showInputDialog("Skriv inn Ansatt Id");
		int id= Integer.parseInt(idInput);
		Ansatt a = ansattDAO.finnAnsattMedId(id);
		
		String stilling = showInputDialog("Skriv inn ny stilling for " + a.getFornavn());
		
		ansattDAO.oppdaterAnsattStilling(id, stilling);
		
		System.out.println("Ny stilling: " + stilling + ", for: " + a.getFornavn());
	}

	private void utlysAlleAnsatte() {
		List<Ansatt> ansatte=ansattDAO.finnAnsatte();
		
		ansatte.forEach((a) -> a.skrivUt(" "));
		
	}

	private void finnAnsattId() {
	String idInput = showInputDialog("Skriv inn Ansatt Id");
	int id = Integer.parseInt(idInput);
	Ansatt a = ansattDAO.finnAnsattMedId(id);
	a.skrivUt(" ");
	}

	private void finnAnsattInitialer() {
		String id = showInputDialog("Skriv inn Ansatt initialer");
		Ansatt a = ansattDAO.finnAnsattMedInitialer(id);
		a.skrivUt(" ");
	}

	public void nyAnsatt() {
	    String initialer=showInputDialog("Skriv inn initialer");
	    String fornavn=showInputDialog("Skriv inn fornavn");
	    String etternavn=showInputDialog("Skriv inn etternavn");
	    String stilling=showInputDialog("Skriv inn stilling");
	    
	    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d/MM/yyyy");
	    String date=showInputDialog("Skriv inn dato (som 17/05/2021)");
	    LocalDate AnsettelseDato=LocalDate.parse(date, dateFormat);
	    String lonnInput=showInputDialog("Skriv inn lønn");
	    BigDecimal lonn = new BigDecimal(lonnInput);
	    String idInput = showInputDialog("Skriv inn Avdeling id");
	    int id = Integer.parseInt(idInput);
	    
	    Ansatt a = new Ansatt(initialer,fornavn,etternavn
	    		  ,stilling,AnsettelseDato, lonn, id);
	    ansattDAO.leggTilAnsatt(a);
	}
}
