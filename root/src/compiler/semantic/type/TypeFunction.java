package compiler.semantic.type;

import java.util.ArrayList;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeBase;

/**
 * Class for TypeFunction.
 */

public class TypeFunction extends TypeBase {
	
	ArrayList<TypeBase> signature;
	TypeSimple retType;
    
    /**
     * Constructor for TypeFunction.
     * @param scope The declaration scope.
     */
    public TypeFunction (ScopeIF scope) {
        super (scope);
    }

    /**
     * Constructor for TypeFunction.
     * @param scope The declaration scope
     * @param name The name of the function.
     */
    public TypeFunction (ScopeIF scope, String name) {
        super (scope, name);
    }
    
    public void setSignature(ArrayList<TypeBase> sig) {
    	signature = sig;
    }
    
    public void setType(TypeSimple ret) {
    	retType = ret;
    }
    
    public TypeSimple getType() {
    	return retType;
    }
    
    public boolean isSignatureEmpty() {
    	return signature.isEmpty();
    }
    
    public ArrayList<TypeBase> getSignature(){
    	return signature;
    }
    
    /**
     * Returns the size of the type.
     * @return the size of the type.
     */
    @Override
    public int getSize ()
    {
        return 1;
    }
}
