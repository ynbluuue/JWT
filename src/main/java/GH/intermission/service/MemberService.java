package GH.intermission.service;

import GH.intermission.domain.Member;
import GH.intermission.domain.Role;

import java.util.List;

public interface MemberService {
    Member saveMember(Member member);
    Role saveRole(Role role);
    void addRoleToMember(String email, String roleName);
    Member getMember(String email);
    List<Member> getMembers();
}
