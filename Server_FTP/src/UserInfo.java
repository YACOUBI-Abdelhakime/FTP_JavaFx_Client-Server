import java.io.File;

public class UserInfo {
    public File curPath;
    public File rootPath;
    public String username;
    public String password;
    public boolean userOk = false ;
	public boolean pwOk = false ;
    
    
    UserInfo(){
		curPath = new File("Users").getAbsoluteFile();
    }
    
	public String AbsToRelativePath(File file) {//canger un chemin absolut a un chemin relative
		String[] strs = file.toString().split("Users");		
		return "."+strs[1];
	}
	
}
