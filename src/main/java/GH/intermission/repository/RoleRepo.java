package GH.intermission.repository;

import GH.intermission.domain.Member;
import GH.intermission.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
        Role findByName(String name);
}
