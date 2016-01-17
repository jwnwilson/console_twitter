import com.jwnwilson.Client;
import com.jwnwilson.ConsoleTwitter;
import com.jwnwilson.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @Author: Noel Wilson
 * @Date: 16/01/2016
 *
 */
public class ConsoleTwitterUnitTest {
    /**
     * Test create User function and getOrCreateUser methods in ConsoleTwitter
     */
    @Test
    public void testCreateConsoleTwitter () {
        ConsoleTwitter conTwitter = new ConsoleTwitter();
        Client client = new Client(conTwitter);
        conTwitter.createUser("Test");

        List<User> userList = conTwitter.getUsers();
        assertEquals(userList.get(0).getUsername(), "Test");
        User testUser = conTwitter.getOrCreateUser("Test2");
        assertEquals(userList.get(1).getUsername(), "Test2");
        testUser = conTwitter.getOrCreateUser("test3");
        assertEquals(testUser.getUsername(), "test3");

        conTwitter = null;
        client = null;
        conTwitter = new ConsoleTwitter();
        client = new Client(conTwitter);
        testUser = conTwitter.getOrCreateUser("test3");
        userList = conTwitter.getUsers();
        assertEquals(userList.get(0).getUsername(), "test3");
    }

    /**
     * Test ConsoleTwitter loadInitData function
     */
    @Test
    public void testLoadInitData(){
        String testFile = "test_data.json";
        ConsoleTwitter conTwitter = new ConsoleTwitter();
        Client client = new Client(conTwitter);
        conTwitter.loadInitData(testFile);

        List<User> userList = conTwitter.getUsers();
        assertEquals(userList.size(), 3);
        User user1 = userList.get(0);
        List<User> follows1 = user1.getFollows();
        User user2 = userList.get(1);
        List<User> follows2 = user2.getFollows();
        User user3 = userList.get(2);

        assertEquals(follows1.get(0).getUsername(), "David");
        assertEquals(follows2.get(0).getUsername(), "Ben");
    }
}
