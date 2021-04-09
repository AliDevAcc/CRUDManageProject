package no.hvl.dat107;

import static javax.swing.JOptionPane.showInputDialog;

import javax.swing.JOptionPane;

import no.hvl.dat107.dao.AnsattDAO;
import no.hvl.dat107.dao.AvdelingDAO;
import no.hvl.dat107.dao.ProsjektDAO;
import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Avdeling;

public class AvdelingGrensesnitt {
	private static String[] ALTERNATIVER = {"Legg inn nytt Avdeling", "Finn avdeling med Id",
	"Utlisting av alle ansatter, inkludert sjef", "Oppdater avdeling for en ansatt "};
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
				case 0 : nyAvdeling();
					break;
				case 1 : finnAvdelingId();
					break;
				case 2 : utlysAlleAnsatte();
				break;
				case 3 :oppdaterAnsatt();
					break;
				default : fortsett = false;
			}
		} //løkke		 
	} //metode

	private void oppdaterAnsatt() {
		String idInput = showInputDialog("Skriv inn Ansatt Id");
		int AnsattId = Integer.parseInt(idInput);
		Ansatt a = ansattDAO.finnAnsattMedId(AnsattId);
		
		String id = showInputDialog("Skriv inn ny Avdeling Id");
		int avdelingId = Integer.parseInt(id);
		
		avdelingDAO.oppdaterAvdeling(a, avdelingId);
	}

	private void utlysAlleAnsatte() {
		
		String id = showInputDialog("Skriv inn ny Avdeling Id");
		int avdelingId = Integer.parseInt(id);
		
		avdelingDAO.utlysAlleAnsatte(avdelingId);
		
	}

	private void finnAvdelingId() {
		String idInput = showInputDialog("Skriv inn Avdeling Id");
		int id = Integer.parseInt(idInput);
		Avdeling a = avdelingDAO.finnAvdelingMedId(id);
		a.skrivUt(" ");
		
	}

	private void nyAvdeling() {
		String idInputAvdeling = showInputDialog("Skriv inn Avdeling Id");
		int id = Integer.parseInt(idInputAvdeling);
		
		String navn = showInputDialog("Skriv inn Avdeling navn");
		
		String idInput = showInputDialog("Skriv inn Ansatt Id");
		int AnsattId = Integer.parseInt(idInput);
		Ansatt a = ansattDAO.finnAnsattMedId(AnsattId);
		
		System.out.println("Lager ny avdeling...");
		avdelingDAO.lagNyAvdeling(id, navn, a);
		
		
	}
}
