package compiler.syntax.nonTerminal;

import compiler.semantic.type.TypeSimple;
import es.uned.lsi.compiler.intermediate.TemporalIF;

public class Expresion extends NonTerminal {

	private TypeSimple type;
	private TemporalIF temp;
	
	//constructors
	public Expresion() {
		super();
		type = null;
		temp = null;
	}
	
	public Expresion(TypeSimple t) {
		super();
		type = t;
		temp = null;
	}
	//
	
	public boolean isBool() {
		return type.getName().equals("Boolean");
	}
	
	//setters	
	public void setType(TypeSimple t) {
		type = t;
	}
	
	public void setTemporal(TemporalIF t) {
		temp = t;
	}
	//
	
	
	//getters	
	public TypeSimple getType() {
		return type;
	}
	
	public TemporalIF getTemporal() {
		return temp;
	}
	//
}
