import com.jwnwilson.Wall;
import com.jwnwilson.User;
import com.jwnwilson.Message;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by noel on 14/01/16.
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
