
package hrremote;

import java.io.Serializable;
import java.util.Objects;
//import vezerlo.JelszoGeneralo;

public class User implements Comparable<User>, Serializable  {

  private String loginName;
  private String titkosjelszo;
  private String jogkor;

  public User(String loginName, String titkosjelszo, String jogkor) {
    this.loginName = loginName;
    this.titkosjelszo = titkosjelszo;
    this.jogkor = jogkor;
  }

  public String getLoginName() {
    return loginName;
  }  

  public String getJelszo() {
    return titkosjelszo;
  }

  public String getJogkor() {
    return jogkor;
  }  

  @Override
  public int compareTo(User o) {
    return this.loginName.compareTo(o.getLoginName());
  }

  @Override
  public String toString() {
    return "User{" + "loginName=" + loginName + ", titkosjelszo=" + titkosjelszo + ", jogkor=" + jogkor + '}';
  }  
}
