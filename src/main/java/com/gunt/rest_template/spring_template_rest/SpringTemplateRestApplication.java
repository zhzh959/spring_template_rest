package com.gunt.rest_template.spring_template_rest;

import com.gunt.rest_template.spring_template_rest.entity.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringTemplateRestApplication {

    static String URL = "http://94.198.50.185:7081/api/users";
    static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {

        userExchangeMetodsRestTemplate();

    }

    private static void userExchangeMetodsRestTemplate() {
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, null, String.class);
        String result = response.getBody();
        List<String> cookies = response.getHeaders().get("Set-Cookie");
        System.out.println(cookies);
        System.out.println(result);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookies", cookies.stream().collect(Collectors.joining(";")));

        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 25);

        getEntity(user, headers);

        createUser(getEntity(user, headers));

        ResponseEntity<String> response1 = restTemplate.exchange(URL, HttpMethod.GET, null, String.class);
        System.out.println(response1.getBody());

        User user1 = new User();
        user1.setId(3L);
        user1.setName("Thomas");
        user1.setLastName("Shelby");
        user1.setAge((byte) 25);

        updateUser(getEntity(user1, headers));

        deleteUser(getEntity(user1, headers));


    }

    private static HttpEntity getEntity(User user, HttpHeaders headers) {
        HttpEntity entity = new HttpEntity<>(user, headers);
        return entity;

    }

    private static void createUser(HttpEntity entity) {
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
        HttpStatus statusCode = response.getStatusCode();
        System.out.println("stausCode - " + statusCode);
        System.out.println("responsBody : " + response.getBody());
        HttpHeaders responseHeaders = response.getHeaders();
        System.out.println("responseHeaders : " + responseHeaders);
    }

    private static void updateUser(HttpEntity<User> entity) {
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        HttpStatus statusCode = response.getStatusCode();
        System.out.println("stausCode - " + statusCode);
        System.out.println(response.getBody());
        HttpHeaders responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);
    }

    private static void deleteUser(HttpEntity<User> entity) {
        ResponseEntity<String> response = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, entity, String.class);
        HttpStatus statusCode = response.getStatusCode();
        System.out.println("stausCode - " + statusCode);
        System.out.println(response.getBody());
        HttpHeaders responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);
    }
}
