package compiler.semantic.symbol;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Class for SymbolParameter.
 */

public class SymbolParameter extends SymbolBase {

	private int address;
   
    /**
     * Constructor for SymbolParameter.
     * @param scope The declaration scope.
     * @param name The symbol name.
     * @param type The symbol type.
     */
		
    public SymbolParameter (ScopeIF scope, String name, TypeIF type) {
        super (scope, name, type);
        address = -1;
    }
    
    public void setAddress(int a) {
    	address = a;
    }
    
    public int getAddress() {
    	return address;
    }
    
}
