package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.semantic.type.TypeBase;

public class Parametro extends NonTerminal {
	
	private String name;
	private TypeBase type;
	
	public Parametro() {
		name = null;
		type = null;
	}
	
	public Parametro(String n, TypeBase t) {
		name = n;
		type = t;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public void setType(TypeBase t) {
		type = t;
	}
	
	public TypeBase getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}

}
