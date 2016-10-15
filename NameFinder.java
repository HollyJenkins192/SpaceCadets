import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NameFinder {
	
	public static void main(String[] args) throws MalformedURLException {
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", "152.78.128.51");
		System.getProperties().put("proxyPort", "3128");
		
		System.out.println("Please enter an email id:");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String iD = null;
 
		try
		{
			iD = br.readLine();
			br.close();
		}
		catch (IOException ioe)
		{
			System.err.println("There was an input error");
		}
		
		if (iD != null){
			URL url = null;
			if (iD.contains("@")){
				iD = iD.substring(0, iD.indexOf("@"));	
			}
			url = makeWebPage(iD);
			getName(url);
		}
			
	}
	
	
	public static URL makeWebPage(String iD){
		String webPageAddress = "http://www.ecs.soton.ac.uk/people/" + iD;
		URL url = null;
		try {
			url = new URL(webPageAddress);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
	
	public static void getName(URL url){
		String name = null;
		
		BufferedReader br;
		try {
			URLConnection connection = (URLConnection) url.openConnection();
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String a = br.readLine();
			outerloop: while(a != null){
				if (a.contains("<h1 class=\"uos-page-title uos-main-title uos-page-title-compressed\" property=\"name\">")){
					for (int i = 0; i < a.length(); i++){
						char x = a.charAt(i);
						if (x == 'n'){
							if (a.charAt(i+1) == 'a'){
								if (a.charAt(i+2) == 'm'){
									if(a.charAt(i+3) == 'e'){
										if (a.charAt(i+4) == '"'){
											if (a.charAt(i+5) == '>'){
												int count = 0;
												innerloop: for (int j = i; j < a.length(); j++){
													if (a.charAt(j) != '<'){
														count += 1;
													}
													else{
														break innerloop;
													}
												}
												name = a.substring(i+6, i + count);
												br.close();
												break outerloop;
											}
										}
									}
								}
							}
							
						}
					}					
				}
				else{
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
