package lab.entities;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class JPAStudent {

    private long id;
    private String name;

    public JPAStudent() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "first_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
