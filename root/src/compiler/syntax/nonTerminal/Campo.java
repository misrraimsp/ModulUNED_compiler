package compiler.syntax.nonTerminal;

import compiler.semantic.type.TypeSimple;

public class Campo extends NonTerminal {

	private String fieldName;
	private TypeSimple type;
	private int index;
	
	public Campo(String n, TypeSimple t) {
		fieldName = n;
		type = t;
		index = -1;
	}
	
	public String getName() {
		return fieldName;
	}
    
    public TypeSimple getType() {
    	return type;
    }
    
    public int getIndex() {
    	return index;
    }
    
    public void setIndex(int i) {
    	index = i;
    }
}
