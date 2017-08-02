package Elena.Chernenkova.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by 123 on 21.07.2017.
 */
@Entity(name = "User")
@NamedQuery(name = "User.isExist", query = "SELECT a FROM User a WHERE a.login = :login")
public class User implements Serializable{
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    String login;
    @Column
    String password;

    @OneToOne(targetEntity = Balance.class)
    private Balance balance;


    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }
}

