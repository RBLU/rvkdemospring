package ch.creagy.rvk.lekauth.LekAuthText.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
public class HelloController {
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("Admin")
    @ResponseBody
    @PreAuthorize("hasAuthority('APPROLE_F_ADMIN')")
    public String Admin(HttpServletRequest request) {
        Jwt jwt = (Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // return "Admin message: " + request.getUserPrincipal() + " principal:  " + jwt.getClaims().toString() + " uniqueName: " + jwt.getClaimAsString("unique_name");
        return "{\"givenName\": \"Hansli\", \"surname\": \"Huber\",\"userPrincipalName\": \"hansli@hans.com\",\"id\": \"666\"}";
    }
}
