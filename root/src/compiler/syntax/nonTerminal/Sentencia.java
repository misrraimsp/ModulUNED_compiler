package compiler.syntax.nonTerminal;

import compiler.semantic.type.TypeSimple;

public class Sentencia extends Axiom {
	
	private TypeSimple retType;//indica el tipo de retorno (null si no contiene RETURN)
	
	//constructors
	public Sentencia() {
		super();
		retType = null;
	}
	
	public Sentencia(TypeSimple t) {
		super();
		retType = t;
	}
	//
	
	public void setRetType(TypeSimple t) {
		retType = t;
	}
	
	public TypeSimple getRetType() {
		return retType;
	}
	
	public boolean containsRet() {
		return (retType != null);
	}

	public boolean isEmpty() {
		return this.getIntermediateCode().isEmpty();
	}
	
}
