package no.hvl.dat107.entity;

import java.util.List;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "Assign_3")
public class Ansatt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String initialer;
    private String fornavn;
    private String etternavn;
    private String stilling;
    private LocalDate AnsettelseDato;
    private BigDecimal lonn;
    private int avdeling_id;
    
    public Ansatt(String initialer, String fornavn, String etternavn, String stilling, LocalDate ansettelseDato,
			BigDecimal lonn, int avdeling_id) {
		this.initialer = initialer;
		this.fornavn = fornavn;
		this.etternavn = etternavn;
		this.stilling = stilling;
		AnsettelseDato = ansettelseDato;
		this.lonn = lonn;
		this.avdeling_id=avdeling_id;
	}

    public Ansatt () {
    	
    }
	@OneToMany(mappedBy="ansatt")
    private List<Prosjektdeltagelse> deltagelser;
    
    @OneToMany(mappedBy="ansatt")
    private List<Avdeling> avdelinger;
    
    public void skrivUt(String innrykk) {
        System.out.printf("%sAnsatt nr %d: %s %s (%s), Stilling: %s, Avdeling: %s", innrykk, id, fornavn, etternavn, initialer, stilling, avdeling_id);
        System.out.println();
    }

	public void skrivUtMedProsjekter() {
        System.out.println();
        skrivUt("");
        deltagelser.forEach(p -> p.skrivUt("\n   "));
    }

    public int getAvdeling_id() {
		return avdeling_id;
	}

	public void setAvdeling_id(int avdeling_id) {
		this.avdeling_id = avdeling_id;
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

	public String getFornavn() {
		return fornavn;
	}

	public String getEtternavn() {
		return etternavn;
	}

    
    public String getInitialer() {
		return initialer;
	}

	public String getStilling() {
		return stilling;
	}

	public LocalDate getAnsettelseDato() {
		return AnsettelseDato;
	}

	public BigDecimal getLonn() {
		return lonn;
	}
	
	public List<Prosjektdeltagelse> getDeltagelser() {
		return deltagelser;
	}


	public void setId(int id) {
		this.id = id;
	}

	public void setInitialer(String initialer) {
		this.initialer = initialer;
	}

	public void setFornavn(String fornavn) {
		this.fornavn = fornavn;
	}

	public void setEtternavn(String etternavn) {
		this.etternavn = etternavn;
	}

	public void setStilling(String stilling) {
		this.stilling = stilling;
	}

	public void setAnsettelseDato(LocalDate ansettelseDato) {
		AnsettelseDato = ansettelseDato;
	}

	public void setLonn(BigDecimal lonn) {
		this.lonn = lonn;
	}

	public void setDeltagelser(List<Prosjektdeltagelse> deltagelser) {
		this.deltagelser = deltagelser;
	}

	public void setAvdelinger(List<Avdeling> avdelinger) {
		this.avdelinger = avdelinger;
	}

	public List<Avdeling> getAvdelinger() {
		return avdelinger;
	}
    


}
