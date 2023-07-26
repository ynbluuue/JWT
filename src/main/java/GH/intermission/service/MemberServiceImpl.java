package GH.intermission.service;

import GH.intermission.domain.Member;
import GH.intermission.domain.Role;
import GH.intermission.repository.MemberRepo;
import GH.intermission.repository.RoleRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class MemberServiceImpl implements MemberService, UserDetailsService {
    private final MemberRepo memberRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepo.findByEmail(username);
        if(member == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else{
            log.info("User found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        member.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(member.getEmail(),member.getPassword(),authorities);
    }

    @Override
    public Member saveMember(Member member) {
        log.info("Saving new member {} to the database", member.getName());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepo.save(member);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToMember(String email, String roleName) {
        log.info("Adding role {} to member {}", roleName, email);
        Member member = memberRepo.findByEmail(email);
        Role role = roleRepo.findByName(roleName);
        member.getRoles().add(role);
    }

    @Override
    public Member getMember(String email) {
        log.info("Fetching member {}", email);
        return memberRepo.findByEmail(email);
    }

    @Override
    public List<Member> getMembers() {
        log.info("Fetching all members");
        return memberRepo.findAll();
    }
}
