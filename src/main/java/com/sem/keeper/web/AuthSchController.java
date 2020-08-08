/**
    MEGA thanks to Szabó Gergely (<gerviba@gerviba.hu>) for his Webschop project
 */
package com.sem.keeper.web;

import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.UserRepository;
import com.sem.keeper.service.UserService;
import hu.gerviba.authsch.AuthSchAPI;
import hu.gerviba.authsch.response.ProfileDataResponse;
import hu.gerviba.authsch.struct.CardType;
import hu.gerviba.authsch.struct.Entrant;
import hu.gerviba.authsch.struct.PersonEntitlement;
import hu.gerviba.authsch.struct.Scope;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptySet;

@Controller
public class AuthSchController {

    private static final Logger log = LoggerFactory.getLogger(AuthSchController.class);

    static final String USER_SESSION_ATTRIBUTE_NAME = "user_id";
    static final String USER_ENTITY_DTO_SESSION_ATTRIBUTE_NAME = "user";

    @Autowired
    AuthSchAPI authSch;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/authSchLogin")
    public String items(HttpServletRequest request, HttpSession session) {
        return "redirect:" + authSch.generateLoginUrl(buildUniqueState(request),
                Scope.BASIC, Scope.DISPLAY_NAME, Scope.MAIL, Scope.ENTRANTS, Scope.EDU_PERSON_ENTILEMENT);
    }

    public String buildUniqueState(HttpServletRequest request) {
        return toSha256(request.getSession().getId()
                + request.getLocalAddr()
                + request.getLocalPort());
    }

    @SneakyThrows(NoSuchAlgorithmException.class)
    public static String toSha256(String str){
        var md = MessageDigest.getInstance("SHA-256");
         var hash = md.digest(str.getBytes(StandardCharsets.UTF_8));
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    @GetMapping("/loggedin")
    private String loggedIn(@RequestParam String code, @RequestParam String state,
                            HttpServletRequest request, HttpSession session) {
        if (!buildUniqueState(request).equals(state))
            return "index?invalid-state";

        Authentication auth = null;
        try {
            var response = authSch.validateAuthentication(code);
            var profile = authSch.getProfile(response.getAccessToken());
            UserEntity user;
            Optional<UserEntity> userFromDb = userRepository.findByAuthSchId(profile.getInternalId().toString());
            if (userFromDb.isPresent()) {
                user = userFromDb.get();
                request.getSession().setAttribute(USER_SESSION_ATTRIBUTE_NAME, user.getId());
                request.getSession().setAttribute(USER_ENTITY_DTO_SESSION_ATTRIBUTE_NAME, user);
                auth = new UsernamePasswordAuthenticationToken(code, state, getAuthorities(user));
                CardType card = cardTypeLookup(profile);
                if (user.getCardType() != card) {
                    userService.updateCard(user, card);
                }
            } else {
                CardType card = cardTypeLookup(profile);

                user = new UserEntity();

                user = userRepository.save(user);
                user.setAuthSchId(profile.getInternalId().toString());
                user.setFullName(profile.getDisplayName());
                user.setKiadhat(false);
                user.setEmail(profile.getMail());
                user.setValid(true);
                user.setCardType(card);
                Optional<PersonEntitlement> SEMMembership =
                        profile.getEduPersonEntitlement()
                                .stream()
                                .filter(e->e.getId()==342)
                                .findFirst();
                ArrayList<String> roles = new ArrayList<>();
                roles.add("ROLE_USER");
                if (SEMMembership.isPresent()) {
                    if (!SEMMembership.get().getTitle().contains("újonc")){
                        roles.add("ROLE_MEMBER");
                        user.setKiadhat(true);
                    }
                }

                user.setRoles(roles);
                user=userRepository.save(user);
                auth = new UsernamePasswordAuthenticationToken(code, state, getAuthorities(user));
                request.getSession().setAttribute(USER_SESSION_ATTRIBUTE_NAME, user.getId());
                request.getSession().setAttribute(USER_ENTITY_DTO_SESSION_ATTRIBUTE_NAME, user);
            }
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            if (auth!=null) {
                auth.setAuthenticated(false);
            }
            e.printStackTrace();
        }
        if (auth != null && auth.isAuthenticated()){
            return "redirect:/";
        } else {
            return "redirect:/?error";
        }
    }

    private static List<GrantedAuthority> getAuthorities (UserEntity user) {
        var roles = user.getRoles();
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    private CardType cardTypeLookup(ProfileDataResponse profile) {
        CardType card = CardType.DO;
        for (Entrant entrant : profile.getEntrants()) {
            if (entrant.getEntrantType().toLowerCase().equals("kb") && card.ordinal() < CardType.KB.ordinal()) {
                card = CardType.KB;
            } else if (entrant.getEntrantType().matches("^[ÁáAa][Bb]$") && card.ordinal() < CardType.AB.ordinal()) {
                card = CardType.AB;
            }
        }
        return card;
    }

}
