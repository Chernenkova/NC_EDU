package Elena.Chernenkova.Service;

import Elena.Chernenkova.entity.Balance;
import Elena.Chernenkova.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.util.List;

/**
 * Created by 123 on 21.07.2017.
 */
public class Service {


    public static EntityManager em = Persistence.createEntityManagerFactory("NCEDU").createEntityManager();

    public static boolean addUser(User user) {
        if(isExist(user.getLogin())) {
            return false;
        }
        user.setBalance(createBalance());
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        return true;
    }

    public static Balance createBalance(){
        em.getTransaction().begin();
        Balance balance = em.merge(new Balance());
        em.getTransaction().commit();
        return balance;
    }

    public static boolean isExist(String login){
        Query query = em.createNamedQuery(Constants.USER_IS_EXIST);
        query.setParameter(Constants.LOGIN, login);
        return query.getResultList().size() != 0;
    }

    public static User getUser(String login){
        Query query = em.createNamedQuery(Constants.USER_IS_EXIST);
        query.setParameter(Constants.LOGIN, login);
        List<User> users = query.getResultList();
        return users.get(0);
    }

    public static boolean isPasswordCorrect(String login, String password){
        Query query = em.createNamedQuery(Constants.USER_IS_EXIST);
        query.setParameter(Constants.LOGIN, login);
        List<User> users = query.getResultList();
        //Если заменить на SingleResult, то почему-то возвращается Object
        User user = users.get(0);
        return user.getPassword().equals(DigestUtils.md5Hex(password));
    }

    public static double getBalance(String login){
        Query query = em.createNamedQuery(Constants.USER_IS_EXIST);
        query.setParameter(Constants.LOGIN, login);
        List<User> users = query.getResultList();
        User user = users.get(0);
        return user.getBalance().getBalance();
    }

    public static Balance setNewBalance(Balance oldBalance, double newBalance){
        em.getTransaction().begin();
        Balance result = em.find(Balance.class, oldBalance.getId());
        result.setBalance(newBalance);
        em.getTransaction().commit();
        return result;
    }

    public static boolean isLoginCorrect(String login){
        if(login.length() != 10) {
            return false;
        }
        try {
            Long.parseLong(login);
        } catch (NumberFormatException e) { return false; }
        return true;
    }

    public static boolean isNewPasswordCorrect(String password){
        if(password.length() < 5) {
            return false;
        }
        if(password.toLowerCase().equals(password)) {
            return false;
        }
        if(password.toUpperCase().equals(password)) {
            return false;
        }
        return true;
    }

    public static String find(String parameter, String s) throws IOException {
        char[] a = s.toCharArray();
        int first = s.indexOf(parameter) + parameter.length() + 1;
        int last = s.lastIndexOf(parameter) - 2;
        StringBuilder result = new StringBuilder();
        result.append(s.substring(first,last));
        return result.toString();
    }

    public static String findJson(String parameter, String s){
        char[] a = s.toCharArray();
        int i = s.indexOf(parameter) + parameter.length() + 3;
        StringBuilder result = new StringBuilder();
        if (parameter.equals(Constants.TYPE_M)) {
            return result.append("").append(a[i - 1]).toString();
        } else {
            while (a[i] != '\"') {
                result.append(a[i++]);
            }
        }
        return result.toString();
    }

    public static class TypeAndBalanceData {
        int typeM;
        String balance;

        public TypeAndBalanceData(int typeM, String balance) {
            this.typeM = typeM;
            this.balance = balance;
        }
    }
}


