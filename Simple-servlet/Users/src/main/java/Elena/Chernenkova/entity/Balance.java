package Elena.Chernenkova.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by 123 on 21.07.2017.
 */
@Entity(name = "Balance")
@NamedQuery(name = "Balance.getUsers", query = "SELECT a FROM Balance a")
public class Balance implements Serializable{
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    double balance;

    public Balance() {
        this.balance = 0;
    }

    public Balance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
