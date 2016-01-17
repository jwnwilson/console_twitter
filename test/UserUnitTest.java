import com.jwnwilson.User;
import com.jwnwilson.Wall;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @Author: Noel Wilson
 * @Date: 16/01/2016
 *
 */
public class UserUnitTest {
    /**
     * Test User constructor
     */
    @Test
    public void testUserCreate(){
        User user = new User("test");
        assertEquals(user.getUsername(), "test");
    }

    /**
     * Test User follows method
     */
    @Test
    public void testFollows(){
        User user1 = new User("test1");
        User user2 = new User("test2");

        user1.follow(user2);
        user2.follow(user1);

        assertEquals(user1.getFollows().get(0), user2);
        assertEquals(user2.getFollows().get(0), user1);
    }
}
