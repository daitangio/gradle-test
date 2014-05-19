package com.gioorgi.jdk8.lambda;

import java.util.ArrayList;
import java.util.List;

/**
 *  This example show how to deal with diamond problem
 * @author jj
 *
 */
public class OverrideExample implements EasyFace,NeverDatePartner {

	

	// The override works, for sure:	
	@Override
	public String dateMe() {		
		return "Em... embarassed: one side of me say\n\t"+EasyFace.super.dateMe()+ 
				" the other say\n\t"+NeverDatePartner.super.dateMe();
	}

	
	public static void main(String[] args) {
		
		// Casting is useless
		
		System.err.println(""+
				(new OverrideExample()).dateMe());
//		System.err.println(""+
//		((EasyFace)(new OverrideExample())).dateMe());
//
//System.err.println(""+
//		((NeverDatePartner)(new OverrideExample())).dateMe());

		
		// You can use method reference like Integer::compare ?...
		
//		Callable<String> ft=  NeverDatePartner::dateMe; 

		// Function<NeverDatePartner,String> ft= (NeverDatePartner p) -> { return p.dateMe(); };
		List<NeverDatePartner> l= new ArrayList<NeverDatePartner>();
		
		// This syntax  works because it 
		l.forEach(NeverDatePartner::dateMe);
		// is translated to something like 
		l.forEach((p)-> { p.dateMe(); });
	}


	@Override
	public String myName() {
		return "CaliforniaGurl";
	}

}
