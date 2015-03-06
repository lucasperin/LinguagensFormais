package automato;

import java.io.Serializable;
import java.util.List;

public class Transicao implements Cloneable, Serializable {
	private static final long serialVersionUID = 3000;

	String simbolo;
	Estado destino;
	
	public Transicao(String simbolo_, Estado destino_){
		simbolo = simbolo_;
		destino = destino_;
	}
	
	public String getSimbolo() {
		return simbolo;
	}
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	public Estado getDestino() {
		return destino;
	}
	public void setDestino(Estado destino) {
		this.destino = destino;
	}
	
	@Override
	public String toString() {
		return simbolo + " -> " + destino;
	}
	@Override
	public boolean equals(Object obj) {
		Transicao t = (Transicao) obj;
		if(t.destino.equals(this.destino) && t.simbolo.compareTo(this.simbolo)==0){
			return true;
		}
		return super.equals(obj);
	}
	
	
	public Transicao copy(List<Estado> estadosNovos) throws CloneNotSupportedException {
		Transicao temp = (Transicao)this.clone();
		temp.destino = null;
		
		for (int i = 0; i < estadosNovos.size(); i++) {
		
			if(destino.equals(estadosNovos.get(i))){
				temp.destino = estadosNovos.get(i);
			}
			
		}
		return temp;
		
	}
}
