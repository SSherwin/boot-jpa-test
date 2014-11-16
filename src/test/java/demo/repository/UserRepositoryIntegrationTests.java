package demo.repository;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.RequestSpecification;
import demo.Application;
import demo.domain.Role;
import demo.domain.User;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles(profiles = "inttest")
@IntegrationTest("server.port:8080")
public class UserRepositoryIntegrationTests {

    final String BASE_URL = "http://localhost:8080";
    final String CONTEXT_URL = "/users/";

    @Autowired
    UserRepository userRepos;

    @Autowired
    RoleRepository roleRepos;

    @Value("${local.server.port}")
    int port;
    User mickey;
    User minnie;
    User pluto;

    @Before
    public void setUp() {
        Role userRole = new Role("USER_ROLE", "User Role");
        userRole = roleRepos.save(userRole);

        mickey = buildUser("mickey", "Mickey", "Mouse", userRole);
        minnie = buildUser("minnie", "Minnie", "Mouse", userRole);
        pluto = buildUser("pluto", "Pluto", "Dog", userRole);

        // Ensue clean
        userRepos.deleteAll();
        userRepos.save(Arrays.asList(mickey, minnie, pluto));

        //set port
        RestAssured.port = port;
    }

    @After
    public void tearDown() {
        userRepos.deleteAll();
        roleRepos.deleteAll();
    }

    @Test
    public void testCanFetchUserById() {
        Long id = mickey.getUserId();

        when().
                get(CONTEXT_URL + "{id}", id).
                then().
                statusCode(HttpStatus.SC_OK).
                body("userName", Matchers.is("mickey")).
                body("surname", Matchers.is("Mouse")).
                body("firstName", Matchers.is("Mickey")).
                body("version", Matchers.is(0));
    }

    @Test
    public void testCanFetchUserRoles() {
        Long id = mickey.getUserId();

        when().
                get(CONTEXT_URL + "{id}/roles", id).
                then().
                statusCode(HttpStatus.SC_OK).
                body("_embedded.roles[0].roleName", Matchers.is("USER_ROLE")).
                body("_embedded.roles[0].roleDescription", Matchers.is("User Role"));
    }

    @Test
    public void testCanFetchAllUserRoles() {
        when().
                get("/users").
                then().
                statusCode(HttpStatus.SC_OK).
                body("_embedded.users.userName", Matchers.hasItems("mickey", "minnie", "pluto")).
                body("_embedded.users.surname",  Matchers.hasItems("Mouse", "Mouse", "Dog")		);
    }

    @Test
    public void testCanUpdateUsersSurname() {
        Long id = mickey.getUserId();

        mickey.setSurname("UpdatedSurname");
        RequestSpecification body = given().
                contentType("application/json").
                body("{ \"surname\" : \"ChangedSurname\" }");

        body.
                when().
                patch("/users/{id}", id).
                then().
                statusCode(HttpStatus.SC_NO_CONTENT);

        when().
                get(CONTEXT_URL + "{id}", id).
                then().
                statusCode(HttpStatus.SC_OK).
                body("userName", Matchers.is("mickey")).
                body("surname", Matchers.is("ChangedSurname")).
                body("firstName", Matchers.is("Mickey")).
                body("version", Matchers.is(1));

        when().
                get(CONTEXT_URL + "{id}/roles", id).
                then().
                statusCode(HttpStatus.SC_OK).
                body("_embedded.roles[0].roleName", Matchers.is("USER_ROLE")).
                body("_embedded.roles[0].roleDescription", Matchers.is("User Role"));
    }




    private User buildUser(final String userName, final String firstName, final String surname, final Role role) {
        User res = new User();
        res.setUserName(userName);
        res.setFirstName(firstName);
        res.setSurname(surname);

        res.getRoles().add(role);

        return res;
    }

}