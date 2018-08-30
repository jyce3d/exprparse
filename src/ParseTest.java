import be.sdlg.expparsing.*;

public class ParseTest {
	public static void main(String args[]) {
		//Automaton automo = new Automaton("3.14159265358+myvariable+(332*222)");
		/*while (automo.getStatus() != Automaton.STATUS_FINAL ) {
			automo.nextTransition();
			System.out.println("value =" + automo.getValue() + "status="+automo.getStatus());
		}*/
		ExprParser ep = new ExprParser("2*3+(4-8*2)*(3-2)-(4*(2/2))"); 	
		System.out.println(""+ep.parse());
		
	}

}
