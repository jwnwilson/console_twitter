import com.jwnwilson.Wall;
import com.jwnwilson.User;
import com.jwnwilson.Message;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @Author: Noel Wilson
 * @Date: 16/01/2016
 */
public class MessageUnitTest {
    @Test
    public void testMessageCreate(){
        User user = new User("test");
        Message message = new Message("Test message", new DateTime(), user);

        assertEquals(message.getMessage(), "Test message");
        assertEquals(message.getOwner(), user);
    }
}
