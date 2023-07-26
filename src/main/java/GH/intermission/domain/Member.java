package GH.intermission.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
    private String email;
    private String password;
    @ManyToMany(fetch= FetchType.EAGER)
    private Collection<Role> roles=new ArrayList<>();
}
