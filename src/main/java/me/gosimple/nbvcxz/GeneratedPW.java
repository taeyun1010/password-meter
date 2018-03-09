package me.gosimple.nbvcxz;

public class GeneratedPW {
	
	
	public String password;
	
	//true if this password contains english transcription of Hanguel
	public boolean containedHanguel;
	
	//explains the mapping between Hanguel and Enlglish
	public String meaning;
	
	GeneratedPW(String password, boolean containedHanguel, String meaning){
		this.password = password;
		this.containedHanguel = containedHanguel;
		this.meaning = meaning;
	}
}
