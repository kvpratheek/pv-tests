package com.sap.pvs.saasprovider.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.pvs.saasprovider.demo.util.MapBuilder;
import com.sap.pvs.saasprovider.demo.util.VcapServiceReader;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@RestController
@RequestMapping(value = "/callback/v1.0")
public class TenantManagement {

    private static final Logger log = LoggerFactory.getLogger(TenantManagement.class);

    private static final String XSAPPNAME = "xsappname";
    private static final String APPNAME = "appName";
    private static final String XSAPPID = "appId";

    @Autowired
    Environment environment;

    @Value("${APPROUTER_DOMAIN}")
    String approuterURLSuffix;

    @GetMapping(path = "/dependencies", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, String>> getDependencies(@RequestParam(required = false) String dependentAppId) {
        VcapServiceReader vcapServiceReader = new VcapServiceReader();

        List<Map<String, String>> dependencies = new ArrayList<>();
        System.out.println("Pratheek : " + vcapServiceReader.getUAAttribute("spa-reuse", XSAPPNAME) );
        dependencies.add(MapBuilder.of(XSAPPNAME, vcapServiceReader.getUAAttribute("spa-reuse", XSAPPNAME)));
//        dependencies.add(MapBuilder.of(XSAPPNAME, vcapServiceReader.getUAAttribute("portal", XSAPPNAME)));
//        dependencies.add(MapBuilder.of(XSAPPNAME, vcapServiceReader.getUAAttribute("html5appsrepo-appruntime", XSAPPNAME)));
        System.out.println("Pratheek : return" + dependencies );
        return dependencies;
    }

    @PutMapping("/tenants/{tenantId}")
    @ResponseStatus(HttpStatus.OK)
    public String putTenant(@PathVariable String tenantId, @RequestBody SubscriptionPayload payload) {
        return String.format("https://%s%s", payload.subscribedSubdomain, approuterURLSuffix);
    }

    @DeleteMapping("/tenants/{tenantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTenant(@PathVariable String tenantId, @RequestBody SubscriptionPayload payload) {
        log.error("Delete called for tenantId {} from application {} ", tenantId, payload.subscriptionAppName);
    }

    public Response requestOAuthTokenFromClientCredentials(final String authorizationEndPoint, final String clientId,
            final String clientSecret) {
        final Map<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", "client_credentials");

        return withBaseSpecification().auth().basic(clientId, clientSecret).contentType(ContentType.URLENC).formParams(formParams)
                .post(authorizationEndPoint + "/oauth/token");
    }

    public RequestSpecification requestSpecificationWithAccessToken(final String accessToken) {
        return withBaseSpecification().header("AUTHORIZATION", "Bearer " + accessToken);
    }

    private RequestSpecification withBaseSpecification() {
        return RestAssured.given().relaxedHTTPSValidation().urlEncodingEnabled(false);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SubscriptionPayload {

        @JsonProperty
        String subscriptionAppId;
        @JsonProperty
        String subscriptionAppName;
        @JsonProperty
        String subscribedTenantId;
        @JsonProperty
        String subscribedSubdomain;
        @JsonProperty
        String subscribedSubaccountId;
        @JsonProperty
        String subscriptionAppPlan;
        @JsonProperty
        String subscriptionAppAmount;
        @JsonProperty
        List<String> dependentServiceInstanceAppIds;
        @JsonProperty
        String eventType;
        @JsonProperty
        String globalAccountGUID;
        @JsonProperty
        String userId;
        @JsonProperty
        String subscribedLicenseType;

    }

}
