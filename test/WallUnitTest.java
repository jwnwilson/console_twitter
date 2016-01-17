import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.junit.Test;
import com.jwnwilson.Wall;
import com.jwnwilson.User;
import com.jwnwilson.Message;

/**
 * @Author: Noel Wilson
 * @Date: 16/01/2016
 */
public class WallUnitTest {
    @Test
    /**
     * Test Wall constructor
     */
    public void testWallCreate(){
        User user = new User("test");
        Wall wall = new Wall(user);

        assertEquals(wall.getOwner(), user);
    }

    @Test
    /**
     * Test Wall addMessage function
     */
    public void testAddMessage(){
        User user = new User("test");
        Wall wall = new Wall(user);
        Message message = new Message("Test message 2", new DateTime("2015-01-01T00:00:00.000Z"), user);

        wall.addMessage("Test message 1");
        wall.addMessage(message);

        assertEquals(wall.getMessages().get(0).getMessage(), "Test message 1");
        assertEquals(wall.getMessages().get(1).getMessage(), "Test message 2");
    }
}
