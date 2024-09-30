import java.util.HashMap;

public class IDandPassword {

	HashMap<String,String> logininfo = new HashMap<String,String>();
	
	IDandPassword(){
		
		logininfo.put("John","1111");
		logininfo.put("Cesar","2222");
		logininfo.put("Maureen","3333");
	}
	
	public HashMap<String, String> getLoginInfo(){
		return logininfo;
	}
}