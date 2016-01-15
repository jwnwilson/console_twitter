package com.jwnwilson;

import java.util.Date;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.PeriodFormatterBuilder;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;

/**
 * Created by noel on 14/01/16.
 */
public class Message {
    private static final Logger LOGGER = Logger.getLogger( Message.class.getName() );
    private String message;
    private DateTime created;

    public Message(String in_message, DateTime now){
        message = in_message;
        created = now;
        LOGGER.info("Created new message: " + message + ", " + created.toString());
    }

    private String messageAge(){
        DateTime now = new DateTime();
        Period period = new Period(created, now);

        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendSeconds().appendSuffix(" seconds ago\n")
                .appendMinutes().appendSuffix(" minutes ago\n")
                .appendHours().appendSuffix(" hours ago\n")
                .appendDays().appendSuffix(" days ago\n")
                .appendWeeks().appendSuffix(" weeks ago\n")
                .appendMonths().appendSuffix(" months ago\n")
                .appendYears().appendSuffix(" years ago\n")
                .printZeroNever()
                .toFormatter();

        String elapsed = formatter.print(period);
        return "(" + elapsed + ")";
    }

    public String toString() {
        return message + " " + messageAge();
    }
}
