package Elena.Chernenkova.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by 123 on 12.09.2017.
 */
@Entity(name = "director")
@NamedQueries({ @NamedQuery(name = "Director.getDirector", query = "SELECT a FROM director a WHERE a.directorName = :directorName"),
                @NamedQuery(name = "Director.getAllDirectors", query = "SELECT a.directorName FROM director a")
})
public class Director implements Serializable{
    @Id
    @GeneratedValue
    private Integer directorId;


    @Column
    String directorName;
    @Column
    String directorNumber;



    /*@ManyToOne(targetEntity = Department.class)
    private Department department;
*/



    public Director(String directorName, String directorNumber) {
        this.directorName = directorName;
        this.directorNumber = directorNumber;
    }

    public Director() {
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getDirectorNumber() {
        return directorNumber;
    }

    public void setDirectorNumber(String directorNumber) {
        this.directorNumber = directorNumber;
    }

}
