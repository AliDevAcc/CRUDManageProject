package no.hvl.dat107.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "Assign_3")
public class Avdeling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String navn;

    @ManyToOne
    @JoinColumn(name="Sjef")
    private Ansatt ansatt;

    public Avdeling() {

	}
    
	public int getId() {
		return id;
	}

	public String getNavn() {
		return navn;
	}

	public Ansatt getAnsatt() {
		return ansatt;
	}

	public Avdeling(int id, String navn, Ansatt ansatt) {
		this.id = id;
		this.navn = navn;
		this.ansatt = ansatt;
	}

    public void skrivUt(String innrykk) {
        System.out.printf("%sAvdeling nr %s: %s, Sjef:%s, %s", innrykk, id,
                 navn,ansatt.getFornavn(), ansatt.getEtternavn());
        System.out.println();
    }
}
