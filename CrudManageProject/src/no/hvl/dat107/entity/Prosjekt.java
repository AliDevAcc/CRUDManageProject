package no.hvl.dat107.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "Assign_3")
public class Prosjekt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String navn;

	public Prosjekt(int id, String navn, String beskrivelse) {
		this.id = id;
		this.navn = navn;
		this.beskrivelse = beskrivelse;
	}


	public Prosjekt() {

	}


	private String beskrivelse;
    
    @OneToMany(mappedBy="prosjekt")
    private List<Prosjektdeltagelse> deltagelser;
    
    public void skrivUt(String innrykk) {
        System.out.printf("%sProsjekt nr %d: %s, %s", innrykk, id, navn, beskrivelse);
        System.out.println();
    }
    
    public void skrivUtMedAnsatte() {
        System.out.println();
        skrivUt("");
        deltagelser.forEach(a -> a.skrivUt("\n   "));
    }

    public void leggTilProsjektdeltagelse(Prosjektdeltagelse prosjektdeltagelse) {
        deltagelser.add(prosjektdeltagelse);
    }

    public void fjernProsjektdeltagelse(Prosjektdeltagelse prosjektdeltagelse) {
        deltagelser.remove(prosjektdeltagelse);
    }

	public int getId() {
		return id;
	}

	public String getNavn() {
		return navn;
	}
	
    
    public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}


	public List<Prosjektdeltagelse> getDeltagelser() {
		return deltagelser;
	}
 
    
}






