package Elena.Chernenkova;

import java.io.IOException;


/**
 * Created by 123 on 21.07.2017.
 */
public class Service {

    public static String find(String parameter, String s) throws IOException {
        char[] a = s.toCharArray();
        int first = s.indexOf(parameter) + parameter.length() + 1;
        int last = s.lastIndexOf(parameter) - 2;
        StringBuilder result = new StringBuilder();
        for(int i = first; i < last; i++) {
            result.append(a[i]);
        }
        return result.toString();
    }

    public static String findJson(String parameter, String s){
        char[] a = s.toCharArray();
        int i = s.indexOf(parameter) + parameter.length() + 3;
        StringBuilder result = new StringBuilder();
        if (parameter.equals(Constants.TYPE_M)) {
            return result.append(a[i - 1]).toString();
        } else {
            while (a[i] != '\"') {
                result.append(a[i++]);
            }
        }
        return result.toString();
    }

    public static class LoginAndPasswordData {
        String type, login, password;

        public LoginAndPasswordData(String type, String login, String password) {
            this.type = type;
            this.login = login;
            this.password = password;
        }
    }

    public static class LoginAndBalanceData {
        String type, login, balance;

        public LoginAndBalanceData(String type, String login, String balance) {
            this.type = type;
            this.login = login;
            this.balance = balance;
        }
    }
}


