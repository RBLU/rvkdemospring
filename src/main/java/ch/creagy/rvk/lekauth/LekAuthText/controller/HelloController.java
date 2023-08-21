package ch.creagy.rvk.lekauth.LekAuthText.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
public class HelloController {


    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
    private static final String GRAPH_ME_ENDPOINT = "https://graph.microsoft.com/v1.0/me";
    private static final String CUSTOM_LOCAL_FILE_ENDPOINT = "http://localhost:8082/webapiB";
    private final WebClient webClient;
    private final OAuth2AuthorizedClientManager auth2AuthorizedClientManager;


    public HelloController(WebClient webClient, OAuth2AuthorizedClientManager auth2AuthorizedClientManager) {
        this.webClient = webClient;
        this.auth2AuthorizedClientManager = auth2AuthorizedClientManager;
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("Admin")
    @ResponseBody
    @PreAuthorize("hasAuthority('APPROLE_F_Admin')")
    public String Admin(HttpServletRequest request) {
        Jwt jwt = (Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // instead of getting the data from the DB we just return hardcoded values
        return "{\"givenName\": \"Hansli\", \"surname\": \"Huber\",\"userPrincipalName\": \"hansli@hans.com\",\"id\": \"666\"}";
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("showtoken")
    @ResponseBody
    public String showtoken(HttpServletRequest request) {
        Jwt jwt = (Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Admin message: " + request.getUserPrincipal() + " principal:  " + jwt.getClaims().toString() + " uniqueName: " + jwt.getClaimAsString("unique_name");
    }



    /**
     * Call the graph resource with OAuth2AuthorizedClientManager.
     *
     * @return Response with graph data
     */
    @GetMapping("callgraph")
    public String callGraphWithAuthorizedClientManager() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest servletRequest = sra.getRequest();
        HttpServletResponse servletResponse = sra.getResponse();

        OAuth2AuthorizeRequest authorizeRequest =
                OAuth2AuthorizeRequest.withClientRegistrationId("graph")
                        .principal(authentication)
                        .attributes(attrs -> {
                            attrs.put(HttpServletRequest.class.getName(), servletRequest);
                            attrs.put(HttpServletResponse.class.getName(), servletResponse);
                        })
                        .build();
        OAuth2AuthorizedClient graph = this.auth2AuthorizedClientManager.authorize(authorizeRequest);
        return callMicrosoftGraphMeEndpoint(graph);
    }

    /**
     * Call the graph resource with @RegisteredOAuth2AuthorizedClient.
     *
     * @param graph authorized client for Graph
     * @return Response with graph data
     */
    @PreAuthorize("hasAuthority('SCOPE_Obo.Graph.Read')")
    @GetMapping("call-graph")
    public String callGraph(@RegisteredOAuth2AuthorizedClient("graph") OAuth2AuthorizedClient graph) {
        return callMicrosoftGraphMeEndpoint(graph);
    }



    /**
     * Call microsoft graph me endpoint
     *
     * @param graph Authorized Client
     * @return Response string data.
     */
    private String callMicrosoftGraphMeEndpoint(OAuth2AuthorizedClient graph) {
        if (null != graph) {
            String body = webClient
                    .get()
                    .uri(GRAPH_ME_ENDPOINT)
                    .attributes(oauth2AuthorizedClient(graph))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            LOGGER.info("Response from Graph: {}", body);
            return null != body ? body.toString() : "failed.";
        } else {
            return "Graph response failed.";
        }
    }



}


