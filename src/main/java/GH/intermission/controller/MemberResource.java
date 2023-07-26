package GH.intermission.controller;

import GH.intermission.domain.Member;
import GH.intermission.domain.Role;
import GH.intermission.service.MemberService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller @RequestMapping("/api") @RequiredArgsConstructor
public class MemberResource {
    private final MemberService memberService;

    @GetMapping("/users")
    public ResponseEntity<List<Member>>getMembers(){
        return ResponseEntity.ok().body(memberService.getMembers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<Member>saveMember(@RequestBody Member member){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/members/save").toUriString());
        return ResponseEntity.created(uri).body(memberService.saveMember(member));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role>saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(memberService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form){
        memberService.addRoleToMember(form.getEmail(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try{
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String email = decodedJWT.getSubject();
                Member member = memberService.getMember(email);
                String access_token= JWT.create()
                        .withSubject(member.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", member.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception exception){
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message ", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}

@Data
class RoleToUserForm{
    private String email;
    private String roleName;
}
