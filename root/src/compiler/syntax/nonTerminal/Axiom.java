package compiler.syntax.nonTerminal;

//import compiler.intermediate.Label;
//import compiler.intermediate.Value;

/**
 * Abstract Class for Axiom non terminal.
 */
public abstract class Axiom extends NonTerminal {
	
	//private Value globalDataSize;//tamano de los datos globales
	//private Label globalName;//nombre del scope global
    
	/**
     * Constructor for Axiom.
     */
    public Axiom() {
        super ();
        //globalDataSize = null;
        //globalName = null;
    }
    /*
    public void setGlobalDataSize(Value size) {
		globalDataSize = size;
	}
	
	public Value getGlobalDataSize() {
		return globalDataSize;
	}
	
    public void setGlobalName(Label name) {
    	globalName = name;
	}
	
	public Label getGlobalName() {
		return globalName;
	}
	*/
    
}
