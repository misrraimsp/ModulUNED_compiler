package compiler.code;

import java.util.Arrays;
import java.util.List;

import compiler.intermediate.Label;
import compiler.intermediate.Temporal;
import compiler.intermediate.Value;
import compiler.intermediate.Variable;
import compiler.semantic.type.TypeSimple;

import es.uned.lsi.compiler.code.ExecutionEnvironmentIF;
import es.uned.lsi.compiler.code.MemoryDescriptorIF;
import es.uned.lsi.compiler.code.RegisterDescriptorIF;
import es.uned.lsi.compiler.intermediate.OperandIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;

/**
 * Class for the ENS2001 Execution environment.
 */

public class ExecutionEnvironmentEns2001 
    implements ExecutionEnvironmentIF
{    
    private final static int      MAX_ADDRESS = 65535; 
    private final static String[] REGISTERS   = {
       ".PC", ".SP", ".SR", ".IX", ".IY", ".A", 
       ".R0", ".R1", ".R2", ".R3", ".R4", 
       ".R5", ".R6", ".R7", ".R8", ".R9"
    };
    
    private RegisterDescriptorIF registerDescriptor;
    private MemoryDescriptorIF   memoryDescriptor;
    
    /**
     * Constructor for ENS2001Environment.
     */
    public ExecutionEnvironmentEns2001 ()
    {       
        super ();
    }
    
    /**
     * Returns the size of the type within the architecture.
     * @return the size of the type within the architecture.
     */
    @Override
    public final int getTypeSize (TypeSimple type)
    {      
        return 1;  
    }
    
    /**
     * Returns the registers.
     * @return the registers.
     */
    @Override
    public final List<String> getRegisters ()
    {
        return Arrays.asList (REGISTERS);
    }
    
    /**
     * Returns the memory size.
     * @return the memory size.
     */
    @Override
    public final int getMemorySize ()
    {
        return MAX_ADDRESS;
    }
           
    /**
     * Returns the registerDescriptor.
     * @return Returns the registerDescriptor.
     */
    @Override
    public final RegisterDescriptorIF getRegisterDescriptor ()
    {
        return registerDescriptor;
    }

    /**
     * Returns the memoryDescriptor.
     * @return Returns the memoryDescriptor.
     */
    @Override
    public final MemoryDescriptorIF getMemoryDescriptor ()
    {
        return memoryDescriptor;
    }
    
    /**
     * Translate a quadruple into a set of final code instructions. 
     * @param cuadruple The quadruple to be translated.
     * @return a quadruple into a set of final code instructions. 
     */
    @Override
    public final String translate (QuadrupleIF quadruple) {
    	StringBuffer b = new StringBuffer();
    	//ORG: establece donde comienza la region de codigo
    	if(quadruple.getOperation().equals("ORG")) {
    		b.append("ORG " + quadruple.getResult());
		}
    	//HALT: detiene la ejecucion
    	if(quadruple.getOperation().equals("HALT")) {
    		b.append("HALT");	
    	}
    	//INL: inserta una etiqueta
    	if(quadruple.getOperation().equals("INL")) {
    		String r = operacion(quadruple.getResult());
    		b.append(r + " : NOP ");
		}
    	//CMP: realiza la resta de los argumentos (ens2001 dependant: el resultado modifica los bits de estado)
    	if(quadruple.getOperation().equals("CMP")) {
    		String op1 = operacion(quadruple.getFirstOperand());
    		String r = operacion(quadruple.getResult());
    		b.append("CMP " + r + ", " + op1);
		}
    	//BZ: salto si bit de estado Z activo (ens2001 dependant: Z activo si resultado = 0)
    	if(quadruple.getOperation().equals("BZ")) {
    		String r = operacion(quadruple.getResult());
    		b.append("BZ " + "/" + r);
		}
    	//BN: salto si bit de estado S activo (ens2001 dependant: S es el bit de signo, activo si negativo)
    	if(quadruple.getOperation().equals("BN")) {
    		String r = operacion(quadruple.getResult());
    		b.append("BN " + "/" + r);
		}
    	//BR: salto incondicional
    	if(quadruple.getOperation().equals("BR")) {
    		String r = operacion(quadruple.getResult());
    		b.append("BR " + "/" + r);
		}
    	//MVA: mueve la direccion de una variable a un temporal
    	if(quadruple.getOperation().equals("MVA")) {
    		Variable v = (Variable) quadruple.getFirstOperand();
    		String r = operacion(quadruple.getResult());
    		if (v.isGlobal()) {
        		b.append("MOVE " + "#" + v.getAddress() + ", " + r);
        	}
        	else {
        		int numSaltos = v.getSalto();
        		b.append("MOVE " + ".IX" + ", " + ".IY" + "\n");
        		while (numSaltos > 0) {
        			b.append("MOVE " + "#-3[.IY]" + ", " + ".R6" + "\n");
        			b.append("MOVE " + ".R6" + ", " + ".IY" + "\n");
       				numSaltos--;
        		}
        		b.append("SUB " + ".IY" + ", " + "#" + v.getAddress() + "\n");
           		if (v.isParameter()) {
           			b.append("MOVE " + ".A" + ", " + ".R8" + "\n");
           			b.append("MOVE " + "[.R8]" + ", " + r);
           		}
           		else {
           			b.append("MOVE " + ".A" + ", " + r);
           		}
       		}
		}
    	//MVP: mueve el contenido de una direccion de memoria a un temporal
    	if(quadruple.getOperation().equals("MVP")) {
    		String op1 = operacion(quadruple.getFirstOperand());
    		String r = operacion(quadruple.getResult());
    		b.append("MOVE " + op1 + ", " + ".R9" + "\n");
    		b.append("MOVE " + "[.R9]" + ", " + r);
		}
    	//MV: carga un valor en un temporal
    	if(quadruple.getOperation().equals("MV")) {
    		String op1 = operacion(quadruple.getFirstOperand());
    		String r = operacion(quadruple.getResult());
    		b.append("MOVE " + op1 + ", " + r);
		}
    	//STP: carga en la direccion contenida en un temporal el contenido de otro temporal
    	if(quadruple.getOperation().equals("STP")) {
    		String op1 = operacion(quadruple.getFirstOperand());//el contenido
    		String r = operacion(quadruple.getResult());//la direccion
    		b.append("MOVE " + r + ", " + ".R9" + "\n");
    		b.append("MOVE " + op1 + ", " + "[.R9]");
		}
    	//ADD: suma el contenido de dos temporales y lo almacena en un tercero
    	if(quadruple.getOperation().equals("ADD")) {
    		String op1 = operacion(quadruple.getFirstOperand());
    		String op2 = operacion(quadruple.getSecondOperand());
    		String r = operacion(quadruple.getResult());
    		b.append("ADD " + op1 + ", " + op2 + "\n");
    		b.append("MOVE " + ".A" + ", " + r);
		}
    	//DIV: divide el contenido de dos temporales y lo almacena en un tercero
    	if(quadruple.getOperation().equals("DIV")) {
    		String op1 = operacion(quadruple.getFirstOperand());
    		String op2 = operacion(quadruple.getSecondOperand());
    		String r = operacion(quadruple.getResult());
    		b.append("DIV " + op1 + ", " + op2 + "\n");
    		b.append("MOVE " + ".A" + ", " + r);
		}
    	//AND: and logico de dos temporales almacenado en un tercero
    	if(quadruple.getOperation().equals("AND")) {
    		String op1 = operacion(quadruple.getFirstOperand());
    		String op2 = operacion(quadruple.getSecondOperand());
    		String r = operacion(quadruple.getResult());
    		b.append("AND " + op1 + ", " + op2 + "\n");
    		b.append("MOVE " + ".A" + ", " + r);
		}
    	//WRSTR: escribe la cadena contenido en la seccion de datos apuntada por la etiqueta
    	if(quadruple.getOperation().equals("WRSTR")) {
    		String r = operacion(quadruple.getResult());
    		b.append("WRSTR /" + r + "\n");
    		b.append("WRCHAR #10");//salto de linea
		}
    	//CADENA: crea una seccion de datos con el valor de la cadena
    	if(quadruple.getOperation().equals("CADENA")) {
    		String op1 = operacion(quadruple.getFirstOperand());
    		b.append(op1 + " : DATA " + quadruple.getResult() + " ");
		}
    	//WRINT: escribe un entero
    	if(quadruple.getOperation().equals("WRINT")) {
    		String r = operacion(quadruple.getResult());
    		b.append("WRINT " + r + "\n");
    		b.append("WRCHAR #10");
		}
    	//WRNL: escribe un salto de linea
    	if(quadruple.getOperation().equals("WRNL")) {
    		b.append("WRCHAR #10");
		}	
    	//BEGIN_SUB: inicia la secuencia de llamada
    	if(quadruple.getOperation().equals("BEGIN_SUB")) {
    		b.append("MOVE " + ".SP" + ", " + ".R0" + "\n");//se almacena en registro el SP antes de empezar la llamada
    		b.append("PUSH #0" + "\n");//valor de retorno
    		b.append("PUSH .IX" + "\n");//el marco de pila se almacena en el RA como enlace de control
    		b.append("PUSH .SR");//estado de la maquina
    	}
    	//SIBLING: establece el enlace de acceso para subp del mismo nivel
    	if(quadruple.getOperation().equals("SIBLING")) {
    		b.append("PUSH #-3[.IX]");
    	}
    	//CHILD: establece el enlace de acceso para subp de un nivel inferior
    	if(quadruple.getOperation().equals("CHILD")) {
    		b.append("PUSH .IX");
    	}
    	//PARAM: introduce un parametro en el RA del subp
    	if(quadruple.getOperation().equals("PARAM")) {
    		String r = operacion(quadruple.getResult());
    		b.append("PUSH " + r);
    	}
    	//CALL_FUN: culmina tanto la secuencia de llamada como la de retorno de una funcion
    	if(quadruple.getOperation().equals("CALL_FUN")) {
    		String op1 = operacion(quadruple.getFirstOperand());
    		String r = operacion(quadruple.getResult());
    		b.append("MOVE " + ".R0" + ", " + ".IX" + "\n");//el SP antes de la llamada se guarda en IX como el nuevo marco de pila
    		b.append("CALL " + "/" + r + "\n");//coloca PC en la pila (dir de retorno) y salta a r
    		b.append("MOVE " + ".IX" + ", " + ".SP" + "\n");//se restaura el SP
    		b.append("MOVE " + "#0[.IX]" + ", " + ".R1" + "\n");//se almacena en registro el valor de retorno
    		b.append("MOVE " + "#-1[.IX]" + ", " + ".R2" + "\n");//se almacena en registro el enlace de control
    		b.append("MOVE " + "#-2[.IX]" + ", " + ".SR" + "\n");//se restaura el estado de la maquina
    		b.append("MOVE " + ".R2" + ", " + ".IX" + "\n");//se restaura el puntero de marco
    		b.append("MOVE " + ".R1" + ", " + op1);//se coloca el valor de retorno en el temporal adecuado del llamante
    	}
    	//CALL_PROC: culmina tanto la secuencia de llamada como la de retorno de un procedimiento
    	if(quadruple.getOperation().equals("CALL_PROC")) {
    		String r = operacion(quadruple.getResult());
    		b.append("MOVE " + ".R0" + ", " + ".IX" + "\n");//el SP antes de la llamada se guarda en IX como el nuevo marco de pila
    		b.append("CALL " + "/" + r + "\n");//coloca PC en la pila (dir de retorno) y salta a r
    		b.append("MOVE " + ".IX" + ", " + ".SP" + "\n");//se restaura el SP
    		b.append("MOVE " + "#-1[.IX]" + ", " + ".R2" + "\n");//se almacena en registro el enlace de control
    		b.append("MOVE " + "#-2[.IX]" + ", " + ".SR" + "\n");//se restaura el estado de la maquina
    		b.append("MOVE " + ".R2" + ", " + ".IX");//se restaura el puntero de marco
    	}
    	//REGALLOC: reserva el espacio adecuado en la pila para el RA
    	if(quadruple.getOperation().equals("REGALLOC")) {
    		String r = operacion(quadruple.getResult());
    		b.append("SUB " + ".IX" + ", " + r + "\n");
    		b.append("MOVE " + ".A" + ", " + ".SP");
    	}
    	//RETURN: coloca el valor de retorno en su espacio reservado en el RA y dispara la secuencia de retorno
    	if(quadruple.getOperation().equals("RETURN")) {
    		String op1 = operacion(quadruple.getFirstOperand());
    		String r = operacion(quadruple.getResult());
    		b.append("MOVE " + r + ", " + "#0[.IX]" + "\n");//uso dir relativo indirecto para resaltar la posicion en el RA
    		b.append("BR " + "/" + op1);//saltar a END_SUB
    	}
    	//END_SUB: inicia la secuencia de retorno
    	if(quadruple.getOperation().equals("END_SUB")) {
    		String r = operacion(quadruple.getResult());
    		String op1 = operacion(quadruple.getFirstOperand());
    		b.append(r + " : SUB " + ".IX" + ", " + op1 + "\n");
    		b.append("MOVE " + ".A" + ", " + ".SP" + "\n");
    		b.append("RET");
    	}
    	if (b.length() == 0) return quadruple.toString();
    	return b.toString();
	}
    
    private String operacion(OperandIF o) {
    	if(o instanceof Variable) {
    		Variable v = (Variable)o;
    		if(v.isGlobal()) return "/" + v.getAddress();
        	else return "#-" + v.getAddress() + "[.IX]";
    	}
    	if(o instanceof Value) {
    		return "#" + ((Value)o).getValue();
		}
    	if(o instanceof Temporal) {
    		Temporal t = (Temporal)o;
    		if(t.isGlobal()) return "/" + t.getAddress();
    		else return "#-" + t.getAddress() + "[.IX]";
		}
    	if(o instanceof Label){
    		return ((Label)o).getName();
    	}
    	return null;
    }
}
