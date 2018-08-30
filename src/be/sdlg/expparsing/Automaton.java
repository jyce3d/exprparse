package be.sdlg.expparsing;

public class Automaton {
	/*iirna status automaton */
	protected String value;
	protected int status;
	protected int pos;
	private String sExpression;
	
	public static final int STATUS_FINAL = 999;
	public static final int STATUS_NUMERIC = 1;
	public static final int STATUS_ALPHA = 2;
	public static final int STATUS_MULT = 4;
	public static final int STATUS_PLUS = 8;
	public static final int STATUS_MINUS = 16;
	public static final int STATUS_DIVI = 32;
	public static final int STATUS_PAR_LEFT = 64;
	public static final int STATUS_PAR_RIGHT = 128;
	public static final int STATUS_EXP = 256;
	public static final int STATUS_INIT = 0;
	public static final int STATUS_ERROR = -1;
	
	public void nextTransition() {
		value="";
		/* Remove the useless spaces */
		while (pos<sExpression.length() && sExpression.charAt(pos) == ' ') pos++;
		/* define final status, end of the expression */
		if (pos>=sExpression.length() )
			status = STATUS_FINAL;
		else {
			/* Detect float values */
			if (sExpression.charAt(pos)>='0' && sExpression.charAt(pos)<='9' || sExpression.charAt(pos)=='.' ) {
				status = STATUS_NUMERIC;
				while (pos<sExpression.length() && (sExpression.charAt(pos)>='0' && sExpression.charAt(pos)<='9' || sExpression.charAt(pos)=='.' )) {
					value = new StringBuilder().append(value).append(sExpression.charAt(pos)).toString();
					pos++;
				}
			} else {
				/* detect variable name or math function */
				if ((sExpression.charAt(pos)>='a' && sExpression.charAt(pos)<='z') || (sExpression.charAt(pos)>='A' && sExpression.charAt(pos)<='Z')
						|| sExpression.charAt(pos) =='_') {
					status = STATUS_ALPHA;
					while (pos<sExpression.length() && ((sExpression.charAt(pos)>='a' && sExpression.charAt(pos)<='z') || (sExpression.charAt(pos)>='A' && sExpression.charAt(pos)<='Z')
					|| sExpression.charAt(pos) =='_')) {
						value = new StringBuilder().append(value).append(sExpression.charAt(pos)).toString();
						pos++;
					}
				}
				else {
					/* Detect operator */
					if (sExpression.charAt(pos) =='*') 
						status = STATUS_MULT;
					else if (sExpression.charAt(pos) == '+')
						status = STATUS_PLUS;
					else if (sExpression.charAt(pos) == '/')
						status = STATUS_DIVI;
					else if (sExpression.charAt(pos) == '-')
						status = STATUS_MINUS;
					else if (sExpression.charAt(pos) == '(')
						status = STATUS_PAR_LEFT;
					else if (sExpression.charAt(pos) == ')')
						status = STATUS_PAR_RIGHT;
					else if (sExpression.charAt(pos) == '^')
						status = STATUS_EXP;
					else {
						status = STATUS_ERROR;
						value = "AUTOMATON_ERROR";
					}
					pos++;
				}
			}
				
		}
		
	}
	public Automaton(String expression) {
		this.sExpression = expression;
		this.pos= 0;
		this.status = STATUS_INIT;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}	