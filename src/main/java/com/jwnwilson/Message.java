package com.jwnwilson;

import java.util.Date;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.PeriodFormatterBuilder;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import java.util.Comparator;

/**
 * Message compare function for use in sort method to easily
 * Sort messages by date.
 */
class MessageComparator implements Comparator<Message> {

    public int compare(Message m1, Message m2)
    {
        return m1.compareTo(m2);
    }
}

/**
 * Created by noel on 14/01/16.
 */
public class Message {

    public static final Logger LOGGER = Logger.getLogger( Message.class.getName() );
    private String message;
    private DateTime created;
    private User owner;

    public Message(String in_message, DateTime now, User user){

        message = in_message;
        created = now;
        owner = user;
        LOGGER.info("Created new message: " + user + " : " + message + ", " + created.toString());
    }

    private String messageAge(){

        DateTime now = new DateTime();
        Period period = new Period(created, now);

        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendSeconds().appendSuffix(" seconds ")
                .appendMinutes().appendSuffix(" minutes ")
                .appendHours().appendSuffix(" hours ")
                .appendDays().appendSuffix(" days ")
                .appendWeeks().appendSuffix(" weeks ")
                .appendMonths().appendSuffix(" months ")
                .appendYears().appendSuffix(" years ")
                .printZeroNever()
                .toFormatter();

        String elapsed = formatter.print(period);
        return "(" + elapsed + "ago )";
    }

    public DateTime getCreated(){

        return created;
    }

    public int compareTo(Message m) {

        if (getCreated() == null || m.getCreated() == null)
            return 0;
        return getCreated().compareTo(m.getCreated()) * -1;
    }

    public String getMessage(){

        return message;
    }

    public String toString() {

        return owner.getUsername() + " - " + message + " " + messageAge();
    }
}
