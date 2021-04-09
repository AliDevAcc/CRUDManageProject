package no.hvl.dat107;

import javax.swing.JOptionPane;

public class Grensesnitt {
	private static String[] ALTERNATIVER = {"Ansatt metoder", "AvdelingMetoder",
			"ProsjektMetoder"};
	private AnsattGrensesnitt ag = new AnsattGrensesnitt();
	private AvdelingGrensesnitt avg = new AvdelingGrensesnitt();
	private ProsjektGrensesnitt prosjekt = new ProsjektGrensesnitt();

	
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
				case 0 : AnsattGrensesnitt();
					break;
				case 1 : AvdelingGrensesnitt();
					break;
				case 2 : ProsjektGrensesnitt();
					break;
				default : fortsett = false;
			}
		} //løkke		 
	} //metode

	private void ProsjektGrensesnitt() {
		prosjekt.meny();
		
	}

	private void AvdelingGrensesnitt() {
		avg.meny();
	}

	private void AnsattGrensesnitt() {
		ag.meny();
		
	}
}
