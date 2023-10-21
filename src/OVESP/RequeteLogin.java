package OVESP;

public class RequeteLogin implements Requete{
    private String login;
    private String password;
    public RequeteLogin(String l,String p) {
        login = l;
        password = p;
    }

    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
}
