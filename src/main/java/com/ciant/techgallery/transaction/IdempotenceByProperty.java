package com.ciant.techgallery.transaction;

public @interface IdempotenceByProperty {
	
	String [] properties() ;
	
	
}
