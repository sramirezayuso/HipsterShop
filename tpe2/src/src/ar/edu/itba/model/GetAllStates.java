package ar.edu.itba.model;
import java.util.List;


public class GetAllStates {
	private Meta meta;
	private List<State> states;//el nombre de la clase no importa, pero el de las variables debe coincidir con el json
	public Meta getMeta() {
		return meta;
	}
	public List<State> getStates() {
		return states;
	}
	@Override
	public String toString() {
		return meta.toString() + states.toString();
	}
}
