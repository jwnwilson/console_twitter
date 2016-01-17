import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.jwnwilson.Message;
import com.jwnwilson.User;
import com.jwnwilson.Client;
import com.jwnwilson.ConsoleTwitter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;

/**
 * @Author: Noel Wilson
 * @Date: 16/01/2016
 *
 * Test functionality of the client and the 4 main commands:
 * read
 * post
 * wall
 * follow
 */
public class ClientFunctionalityTest {
    /**
     * Test Client Read function
     */
    @Test
    public void testClientRead(){
        ConsoleTwitter consoleTwitter = new ConsoleTwitter();
        Client client = new Client(consoleTwitter);
        consoleTwitter.loadInitData("test_data.json");

        client.processInput("David");
        assertTrue(client.getLastOutput().contains("David - Test Message"));
    }

    /**
     * Test Client Post function
     */
    @Test
    public void testClientPost(){
        ConsoleTwitter consoleTwitter = new ConsoleTwitter();
        Client client = new Client(consoleTwitter);
        consoleTwitter.loadInitData("test_data.json");

        client.processInput("David -> Test Message");
        List<Message> davidMessages = consoleTwitter.getUser("David").getWall().getMessages();
        assertTrue(davidMessages.get(davidMessages.size()-1).getMessage().equals("Test Message"));
    }

    /**
     * Test Client Wall function
     */
    @Test
    public void testClientWall(){
        ConsoleTwitter consoleTwitter = new ConsoleTwitter();
        Client client = new Client(consoleTwitter);
        consoleTwitter.loadInitData("test_data.json");

        client.processInput("David wall");
        assertTrue(client.getLastOutput().contains("David - Test Message 1"));
    }

    /**
     * Test Client Follow function
     */
    @Test
    public void testClientFollow(){
        ConsoleTwitter consoleTwitter = new ConsoleTwitter();
        Client client = new Client(consoleTwitter);
        consoleTwitter.loadInitData("test_data.json");

        client.processInput("David follows Zoe");
        List<User> follows = consoleTwitter.getUser("David").getFollows();
        assertTrue(follows.get(follows.size()-1).getUsername().equals("Zoe"));
    }
}
