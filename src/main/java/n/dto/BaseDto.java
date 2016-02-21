package n.dto;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class BaseDto  implements Serializable {
//    @Id
//    @SequenceGenerator(name="pk_sequence",sequenceName="entity_id_seq", allocationSize=1)
//    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
//    @Column(name="id", unique=true, nullable=false)
    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
