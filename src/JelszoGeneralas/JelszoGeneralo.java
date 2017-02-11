
package JelszoGeneralas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import hrremote.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class JelszoGeneralo {
  private static ArrayList<User> felhasznaloListaXMLbe=new ArrayList<>();  
  private static ArrayList<User> felhasznaloListaXMLbol=new ArrayList<>();
  
  private static void feltoltFelhasznaloLista() {
    User fh1=new User("admin1", kodolas("12345"), "HR1");
    User fh2=new User("admin2", kodolas("54321"), "HR2");
    User fh3=new User("admin3", kodolas("12345"), "HR1");
    felhasznaloListaXMLbe.add(fh1);
    felhasznaloListaXMLbe.add(fh2);    
    felhasznaloListaXMLbe.add(fh3);    
  }
  
  public static String kodolas(String clearText) {
		String titkositottJelszo = null;
		try {
			titkositottJelszo = titkosito(clearText);
		}catch(NoSuchAlgorithmException ex) {
			System.err.println("Nincs ilyen algoritmus");
		}
		return titkositottJelszo;
	}
  
  private static String titkosito(String jelszo) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		byte[] sb = md.digest(jelszo.getBytes()); 
		StringBuffer hexText = new StringBuffer();
		for (int i=0; i<sb.length; i++) {
			String hex = Integer.toHexString(0xFF & sb[i]);
			if(hex.length() == 1)
				hexText.append('0');
			hexText.append(hex);
		}		
		return hexText.toString();
  }
    
  private static void xmlTartalmatKészít2(File xmlFájl) {
     try {
      Document xmlUser=DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
      Element gyoker=xmlUser.createElement("Users");
      xmlUser.appendChild(gyoker);
      for(User fh: felhasznaloListaXMLbe) {
        Element elem=xmlUser.createElement("User");
        gyoker.appendChild(elem);
        Element userName=xmlUser.createElement("Name");
        userName.appendChild(xmlUser.createTextNode(fh.getLoginName()));
        Element userPassword=xmlUser.createElement("Password");
        userPassword.appendChild(xmlUser.createTextNode(fh.getJelszo().toString()));
        Element userRole=xmlUser.createElement("Role");
        userRole.appendChild(xmlUser.createTextNode(fh.getJogkor()));
        elem.appendChild(userName);
        elem.appendChild(userPassword);
        elem.appendChild(userRole);
      }
      DOMSource honnan=new DOMSource(xmlUser);
      StreamResult hová=new StreamResult(xmlFájl);
      TransformerFactory.newInstance().newTransformer().transform(honnan, hová);
    }
    catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    catch (TransformerException e) {
      e.printStackTrace();
    } 
  }
  
  private static void xmlBeolvasas(File xmlFájl) {    
    Document d=null;
		try {
			d=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFájl); //eloforditas?
		}
    catch(ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	  NodeList userLista=d.getDocumentElement().getElementsByTagName("User");
  	for(int i=0; i<userLista.getLength(); i++) {
		  Element user=(Element)userLista.item(i);
      Node loginNameTag=user.getElementsByTagName("Name").item(0);
      String loginName=loginNameTag.getFirstChild().getNodeValue();
      Node loginPasswordTag=user.getElementsByTagName("Password").item(0);
      String loginPassword=loginPasswordTag.getFirstChild().getNodeValue();
      Node loginRoleTag=user.getElementsByTagName("Role").item(0);
      String loginRole=loginRoleTag.getFirstChild().getNodeValue();
      try {
      felhasznaloListaXMLbol.add(new User(loginName, loginPassword, loginRole));
      } catch (Exception e) {
        e.printStackTrace();
      }
	  }
  }
  
  private static void kiir(ArrayList<User> UserLista) {
    for (User user : UserLista) {
      System.out.println(user); 
    }
    System.out.println();
  }
  
  public static void main(String[] args) {
    File xmlFájl=new File("./src/modell/userek.xml");
    String proba1="12345";
    feltoltFelhasznaloLista();
    kiir(felhasznaloListaXMLbe);
    xmlTartalmatKészít2(xmlFájl);
    xmlBeolvasas(xmlFájl);
    kiir(felhasznaloListaXMLbol);
    boolean egyezik1=felhasznaloListaXMLbol.get(0).getJelszo().equals(kodolas(proba1));
    System.out.println("Egyezik az elso user jelszava a \"12345\"-el? (igen)"+(egyezik1?"igen":"nem"));
    boolean egyezik2=felhasznaloListaXMLbol.get(0).getJelszo().equals( felhasznaloListaXMLbol.get(1).getJelszo() );
    System.out.println("Egyezik az elso user jelszava a masodikeval? (nem)"+(egyezik2?"igen":"nem"));
    boolean egyezik3=felhasznaloListaXMLbol.get(0).getJelszo().equals( felhasznaloListaXMLbol.get(2).getJelszo() );
    System.out.println("Egyezik az elso user jelszava a harmadikeval? (igen)"+(egyezik3?"igen":"nem"));
  }     
}

//tmapont lehet: http://szit.hu/doku.php?id=oktatas:programoz%C3%A1s:java:java_xml