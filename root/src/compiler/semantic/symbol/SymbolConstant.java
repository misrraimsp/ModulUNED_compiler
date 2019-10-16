package compiler.semantic.symbol;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Class for SymbolConstant.
 */

public class SymbolConstant extends SymbolBase {
	
	private Object value;
		
    /**
     * Constructor for SymbolConstant.
     * @param scope The declaration scope.
     * @param name The symbol name.
     * @param value The symbol value.
     * @param type The symbol type.
     */
    public SymbolConstant(ScopeIF scope, String name, Object value, TypeIF type) {
        super(scope, name, type);
        this.value = value;
    }
    
    public Object getValue() {
    	return value;
    }
}
