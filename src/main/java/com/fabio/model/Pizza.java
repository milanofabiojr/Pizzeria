package com.fabio.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pizza")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="pizza")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;

    @ManyToOne
    @JoinColumn(name="id_impasto")
    private Impasto impasto;

    @ManyToOne
    @JoinColumn(name="id_utente")
    private Utente utente;
    
    @ManyToMany
    @JoinTable(name = "pizza_ingrediente",
            joinColumns = @JoinColumn(name = "id_pizza"),
            inverseJoinColumns = @JoinColumn(name = "id_ingrediente"))
    private List<Ingrediente> ingredienti = new ArrayList<>();
    
    public Pizza() {}
    public Pizza(String nome, Impasto impasto, Utente utente) {
        this.nome = nome; this.impasto = impasto; this.utente = utente;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Impasto getImpasto() {
		return impasto;
	}

	public void setImpasto(Impasto impasto) {
		this.impasto = impasto;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	public List<Ingrediente> getIngredienti() { return ingredienti; }
    public void setIngredienti(List<Ingrediente> ingredienti) { this.ingredienti = ingredienti; }
}
