package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.syntax.nonTerminal.NonTerminalIF;

/**
 * Abstract class for non terminals.
 */
public abstract class NonTerminal implements NonTerminalIF {
    
	private List<QuadrupleIF> intermediateCode;
    
    /**
     * Constructor for NonTerminal.
     */
    public NonTerminal() {
        super();
        this.intermediateCode = new ArrayList<QuadrupleIF>();
    }

    /**
     * Returns the intermediateCode.
     * @return Returns the intermediateCode.
     */
    public List<QuadrupleIF> getIntermediateCode() {
        return intermediateCode;
    }

    /**
     * Sets The intermediateCode.
     * @param intermediateCode The intermediateCode to set.
     */
    public void setIntermediateCode(List<QuadrupleIF> intermediateCode) {
        this.intermediateCode = intermediateCode;
    }
    
    /*
     * Mueve al final del CI todas las CADENAS
     */
    public void sortIntermediateCode() {
    	List<QuadrupleIF> noCadenas = new ArrayList<QuadrupleIF>();
    	List<QuadrupleIF> cadenas = new ArrayList<QuadrupleIF>();
    	List<QuadrupleIF> nuevoIC = new ArrayList<QuadrupleIF>();
    	for (QuadrupleIF q :intermediateCode) {
    		if (q.getOperation().equals("CADENA")) {
    			cadenas.add(q);
    		}
    		else {
    			noCadenas.add(q);
    		}
    	}
    	nuevoIC.addAll(noCadenas);
    	nuevoIC.addAll(cadenas);
    	intermediateCode = nuevoIC;
    }
    
}