package com.dariotintore.tesi.exerciseservice.Assignment;


public class Student {
	private String nome;
    private String esercizio;
    private String inizio;
    private String fine;
    private boolean consegnato;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEsercizio() {
		return esercizio;
	}
	public void setEsercizio(String esercizio) {
		this.esercizio = esercizio;
	}
	public String getInizio() {
		return inizio;
	}
	public void setInizio(String inizio) {
		this.inizio = inizio;
	}
	public String getFine() {
		return fine;
	}
	public void setFine(String fine) {
		this.fine = fine;
	}
	public boolean isConsegnato() {
		return consegnato;
	}
	public void setConsegnato(boolean consegnato) {
		this.consegnato = consegnato;
	}

}
