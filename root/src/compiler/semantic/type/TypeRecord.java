package compiler.semantic.type;


import java.util.ArrayList;
import java.util.Iterator;

import compiler.syntax.nonTerminal.Campo;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeBase;

/**
 * Class for TypeRecord.
 */


public class TypeRecord extends TypeBase {   
	
	private ArrayList<Campo> campos;
	
    /**
     * Constructor for TypeRecord.
     * @param scope The declaration scope.
     */
    public TypeRecord (ScopeIF scope) {
        super (scope);
        campos = new ArrayList<Campo>();
    }

    /**
     * Constructor for TypeRecord.
     * @param scope The declaration scope.
     * @param name The name of the type.
     */
    public TypeRecord (ScopeIF scope, String name) {   
        super (scope, name);
        campos = new ArrayList<Campo>();
    }
   
    /**
     * Constructor for TypeRecord.
     * @param record The record to copy.
     */
    public TypeRecord (TypeRecord record) {
        super (record.getScope(), record.getName());
        campos = new ArrayList<Campo>(record.getCampos());
    } 
    
    public ArrayList<Campo> getCampos(){
    	return campos;
    }
    
    public void addCampo(Campo c) {
		campos.add(c);
	}
    
    public void addCampo(int index, Campo c) {
		campos.add(index,c);
	}
    
    public boolean containsName(String fieldName) {
		Campo c = getCampo(fieldName);
		if (c == null) return false;
		return true;
	}
    
    public TypeSimple getType(String fieldName) {
		Campo c = getCampo(fieldName);
		if (c == null) return null;
		return c.getType();
	}
    
    public int getIndex(String fieldName) {
    	Campo c = getCampo(fieldName);
		if (c == null) return -1;
		return c.getIndex();
    }
    
    /**
     * Returns the size of the type.
     * @return the size of the type.
     */
    @Override
    public int getSize() {
        return campos.size();
    }
    
    public void setIndexes() {
    	Iterator<Campo> it = campos.iterator();
    	int i = 0;
    	while (it.hasNext()) {
    		Campo c = it.next();
    		c.setIndex(i);
    		i = i + 1;
    	}
    }
    
    private Campo getCampo(String fieldName) {
		Iterator<Campo> it = campos.iterator();
		while(it.hasNext()) {
			Campo c = it.next();
			if (c.getName().equals(fieldName)) return c;
		}
		return null;
	}
    
    //for debugging purposes
    public String print() {
    	Iterator<Campo> it = campos.iterator();
    	String out = "";
    	while (it.hasNext()) {
    		Campo c = it.next();
    		out = out + c.getName() + "(" + c.getType().getName() + "," + c.getIndex() + ")" + "\n";
    	}
    	return out;
    }

}
