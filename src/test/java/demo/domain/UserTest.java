/**
 * 
 */
package demo.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserTest {

	private User testUser;
	@Before
	public void setUp() {
		testUser = new User();
		testUser.setUserId(1L); // This must always be set.
	}

	@Test
	public void testUser() {
		assertThat(testUser, notNullValue());
	}

	/**
	 * Test method for {@link demo.domain.User#getUserId()}.
	 */
	@Test
	public void testGetUserId() {
		assertThat(testUser.getUserId(), equalTo(1L));
	}

	/**
	 * Test method for {@link demo.domain.User#setUserId(long)}.
	 */
	@Test
	public void testSetUserId() {
		User newUser = new User();
		newUser.setUserId(10L); 
		assertThat(newUser.getUserId(), equalTo(10L));
	}

	/**
	 * Test method for {@link demo.domain.User#getActive()}.
	 */
	@Test
	public void testGetSetActive() {
		testUser.setActive(1); 
		assertThat(testUser.getActive(), equalTo(1));
		testUser.setActive(0); 
		assertThat(testUser.getActive(), equalTo(0));
	}


	/**
	 * Test method for {@link demo.domain.User#getDateJoined()}.
	 */
	@Test
	public void testGetSetDateJoined() {
		final Date now = new Date();
		testUser.setDateJoined(now); 
		assertThat(testUser.getDateJoined(), sameInstance(now));
	}

	/**
	 * Test method for {@link demo.domain.User#getFirstName()}.
	 */
	@Test
	public void testGetSetFirstName() {
		final String expected = "FirstName";
		testUser.setFirstName(expected); 
		assertThat(testUser.getFirstName(), equalTo(expected));
	}

	/**
	 * Test method for {@link demo.domain.User#getSurname()}.
	 */
	@Test
	public void testGetSurname() {
		final String expected = "Surname";
		testUser.setSurname(expected); 
		assertThat(testUser.getSurname(), equalTo(expected));
	}

	/**
	 * Test method for {@link demo.domain.User#getUserName()}.
	 */
	@Test
	public void testGetUserName() {
		final String expected = "Username";
		testUser.setUserName(expected); 
		assertThat(testUser.getUserName(), equalTo(expected));
	}

	/**
	 * Test method for {@link demo.domain.User#getVersion()}.
	 */
	@Test
	public void testGetVersion() {
		final long expected = 23;
		testUser.setVersion(expected);
		assertThat(testUser.getVersion(), equalTo(expected));
	}

}
