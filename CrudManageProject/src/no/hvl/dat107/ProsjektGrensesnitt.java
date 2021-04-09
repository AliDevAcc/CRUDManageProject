package no.hvl.dat107;

import static javax.swing.JOptionPane.showInputDialog;

import javax.swing.JOptionPane;

import no.hvl.dat107.dao.AnsattDAO;
import no.hvl.dat107.dao.AvdelingDAO;
import no.hvl.dat107.dao.ProsjektDAO;
import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Prosjekt;

public class ProsjektGrensesnitt {

	private static String[] ALTERNATIVER = {"Legg inn nytt Prosjekt","Legg til prosjektdeltagelse",
			"Fjern prosjektdeltagelse", "Legg til timer i prosjekt for ansatt", "Skriv ut info om prosjekt"};
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
				case 0 : nyProsjekt();
					break;
				case 1 : leggTilDeltagelse();
					break;
				case 2 : fjernDeltagelse();
					break;
				case 3 : leggTilTimer();
				break;
				case 4 : info();
				break;
				default : fortsett = false;
			}
		} //løkke		 
	} //metode

	private void leggTilTimer() {
		
		String idInput = showInputDialog("Skriv inn Ansatt Id");
		int id2= Integer.parseInt(idInput);
		
		String idInputProsjekt = showInputDialog("Skriv inn Prosjekt Id");
		int id = Integer.parseInt(idInputProsjekt);
		
		String idInputProsjekt2 = showInputDialog("Skriv inn timer siden sist til prosjekt");
		int timer = Integer.parseInt(idInputProsjekt2);
		
		ansattDAO.leggTilTimer(id2, id, timer);
	}

	private void fjernDeltagelse() {
		
		String idInputProsjekt = showInputDialog("Skriv inn Prosjekt Id");
		int id = Integer.parseInt(idInputProsjekt);

		Prosjekt p = prosjektDAO.finnProsjektMedId(id);
		
		String idInput = showInputDialog("Skriv inn Ansatt Id");
		int id2= Integer.parseInt(idInput);
		Ansatt a = ansattDAO.finnAnsattMedId(id2);
		
		String input = showInputDialog("Skriv inn Prosjektdeltagelse Id");
		int id3 = Integer.parseInt(input);

		
		ansattDAO.slettProsjektdeltagelse(a, p, id3);
	}

	private void leggTilDeltagelse() {
		String idInputProsjekt = showInputDialog("Skriv inn Prosjekt Id");
		int id = Integer.parseInt(idInputProsjekt);

		Prosjekt p = prosjektDAO.finnProsjektMedId(id);
		
		String idInput = showInputDialog("Skriv inn Ansatt Id");
		int id2= Integer.parseInt(idInput);
		Ansatt a = ansattDAO.finnAnsattMedId(id2);
		
		ansattDAO.registrerProsjektdeltagelse(a, p);
		
	}

	private void nyProsjekt() {
			String idInputProsjekt = showInputDialog("Skriv inn Prosjekt Id");
			int id = Integer.parseInt(idInputProsjekt);
			
			String navn = showInputDialog("Skriv inn Prosjekt navn");
			
			String beskrivelse = showInputDialog("Skriv inn Prosjekt beskrivelse");
			
			Prosjekt p = new Prosjekt(id,navn,beskrivelse);
			System.out.println("Lager ny prosjekt...");
			prosjektDAO.leggTilProsjekt(p);

			p.skrivUt(" ");
			
	}
	
	private void info() {
		String idInputProsjekt = showInputDialog("Skriv inn Prosjekt Id");
		int id = Integer.parseInt(idInputProsjekt);

		Prosjekt p = prosjektDAO.finnProsjektMedId(id);
		
		p.skrivUtMedAnsatte();
	}
	
	
	
}
