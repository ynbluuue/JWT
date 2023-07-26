package GH.intermission.repository;

import GH.intermission.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, Long> {
    Member findByEmail(String Email);
}
