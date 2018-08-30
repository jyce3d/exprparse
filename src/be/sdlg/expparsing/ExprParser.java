package be.sdlg.expparsing;

public class ExprParser {
	ContextInt context;
	Automaton automo;
	public ExprParser(String expression) {
		this.automo = new Automaton(expression);	
		//this.automo.nextTransition(); /* start browsing the automaton statuses */
	}

	/* Definition de la regle : Nombre | variable | (Expression) | "text" | -Atomic | math_func(Atomic)
	   avec math_func = {sin,cos, tan, asin, acos, atan, exp, ln  */
	float getAtomic() {
		/* Il ne peut y avoir qu'un seul Atome */
		float inter=0;
		if (automo.getStatus() == Automaton.STATUS_NUMERIC) {
			inter = Float.valueOf(automo.getValue());
			automo.nextTransition();
		} /*else if (automo.getStatus() == Automaton.STATUS_MINUS) {
			inter = - Float.valueOf(automo.getValue());
			automo.nextTransition();	
		} else if (automo.getStatus() == Automaton.STATUS_ALPHA) {
			if (automo.getValue().equals("sin")) {
				inter = (float) Math.sin(Float.valueOf(automo.getValue()));
				automo.nextTransition();	
			}
		}*/ 
		else if (automo.getStatus() == Automaton.STATUS_PAR_LEFT) {
				automo.nextTransition();
				inter = getExpression();
				if (automo.getStatus() != Automaton.STATUS_PAR_RIGHT) {
					System.out.println("Error ");
				}
				automo.nextTransition();
				
		} 
	
			
		
		return inter;
		
		
	}
	/* Definition de la regle: F=Atome^F | Atome */
	float getFactor() {
		float inter=getAtomic();
		boolean bEmpty = false;
		while (bEmpty == false) {
			if (automo.getStatus() == Automaton.STATUS_EXP) {
				inter=(float) Math.pow(inter, getAtomic());
			} else bEmpty= true;
			
		}
		return inter;
		
		
	}
	/* T -> F | T-> T*F | T-> T/F */
	float getTerm() {
		float inter=getFactor();
		boolean bEmpty = false;
		while (bEmpty == false) {
			if (automo.getStatus() == Automaton.STATUS_MULT) {
				this.automo.nextTransition();
				inter*=getTerm(); 
			}
			else if (automo.getStatus() == Automaton.STATUS_DIVI) {
						this.automo.nextTransition();
						inter/=getTerm(); } 
			else
				bEmpty=true;
		}
		return inter;
		
	}
	/* E-> E+T | E-T | T */
	float getExpression() {
		float inter = getTerm();
		boolean bEmpty = false;
		while (bEmpty == false )  /*Tant qu'il y a des plus et des moins dans l'expression */{
			if (automo.getStatus() == Automaton.STATUS_PLUS) {
			    this.automo.nextTransition();
				inter+=getExpression();
			}
			else 
				if (automo.getStatus() == Automaton.STATUS_MINUS) {
					this.automo.nextTransition();
					inter -= getExpression();
				}
			 else 
			 bEmpty = true;
		}
		return inter;
	}
	 public float parse() {
		 automo.nextTransition();
		 return getExpression();
		 
	 }
}
