import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NameFinder {
	
	public static void main(String[] args) {
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", "152.78.128.51");
		System.getProperties().put("proxyPort", "3128");
		
		System.out.println("Please enter an email id:");
		
		try{
			String iD = bReader.readLine();			
			while (bReader.readLine() != null){
				if (iD.contains("@")){
					iD = iD.substring(0, iD.indexOf("@"));
					URL url = makeWebPage(iD);
					getName(url);
					bReader.close();
					
					
				}
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	
	public static URL makeWebPage(String iD) throws MalformedURLException{
		String webPageAddress = "www.ecs.soton.ac.uk/people/" + iD;
		URL url = new URL(webPageAddress);
		return url;
	}
	
	@SuppressWarnings("unused")
	public static void getName(URL url){
		String name = null;
		
		BufferedReader br;
		try {
			URLConnection connection = (URLConnection) url.openConnection();
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String a = br.readLine();
			outerloop: while(a != null){
				if (a.contains("<h1 class=\"uos-page-title uos-main-title uos-page-title-compressed\" property=\"name\">")){
					for (Integer i = 0; i < a.length(); i++){
						name = a.substring(i, a.length()-5);
						br.close();
						break outerloop;
					}
				a = br.readLine();
				}
			}			
			if (name == null){
				name = "Name not found";
			}
		}
		catch (IOException e) {
			System.out.println("URL could not be reached");
			e.printStackTrace();
		}
		System.out.println("Their name is: " + name);
	}
}
