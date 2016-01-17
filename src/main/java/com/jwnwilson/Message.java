package com.jwnwilson;

import java.util.Date;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.PeriodFormatterBuilder;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import java.util.Comparator;

/**
 * @Author: Noel Wilson
 *
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
 * @Author: Noel Wilson
 * @Date: 16/01/2016
 *
 * Message object holds message string, date created and a link to the user who owns
 * this message
 */
public class Message {
    public static final Logger LOGGER = Logger.getLogger( Message.class.getName() );
    private String message;
    private DateTime created;
    private User owner;

    /**
     * Message constructor, Message has to be initialized with params
     *
     * @param inMessage String message to save in message
     * @param now DateTime object of when the message was created
     * @param user User object of who the message belongs to
     */
    public Message(String inMessage, DateTime now, User user){
        message = inMessage;
        created = now;
        owner = user;
        LOGGER.info("Created new message: " + user + " : " + message + ", " + created.toString());
    }

    /**
     * Return a formatted age String of the message in the format:
     * (2 weeks 1 days ago )
     *
     * Only the 2 most significant digits are shown this is controlled by the numFigures variable
     *
     * @return String formatted Message age
     */
    private String messageAge(){
        DateTime now = new DateTime();
        Period period = new Period(created, now);
        PeriodFormatterBuilder builder = new PeriodFormatterBuilder();
        int figures = 0;
        int numFigures = 2;

        int test = period.getMonths();

        if( period.getYears() > 0 && figures < numFigures){
            builder.appendYears().appendSuffix(" years ");
            figures++;
        }
        if( period.getMonths() > 0 && figures < numFigures) {
            builder.appendMonths().appendSuffix(" months ");
            figures++;
        }
        if( period.getWeeks() > 0 && figures < numFigures){
            builder.appendWeeks().appendSuffix(" weeks ");
            figures++;
        }
        if( period.getDays() > 0 && figures < numFigures){
            builder.appendDays().appendSuffix(" days ");
            figures++;
        }
        if( period.getHours() > 0 && figures < numFigures){
            builder.appendHours().appendSuffix(" hours ");
            figures++;
        }
        if( period.getMinutes() > 0 && figures < numFigures){
            builder.appendMinutes().appendSuffix(" minutes ");
            figures++;
        }
        if( period.getSeconds() > 0 && figures < numFigures){
            builder.appendSeconds().appendSuffix(" seconds ");
            figures++;
        }

        PeriodFormatter formatter = builder.minimumPrintedDigits(2).toFormatter();

        String elapsed = formatter.print(period);
        return "(" + elapsed + "ago )";
    }

    /**
     * Get created DateTime for this message
     *
     * @return DateTime Message created DateTime object
     */
    public DateTime getCreated(){
        return created;
    }

    /**
     * Compare Message dates, this function is to be used by the Collections.sort() method
     * Will return Messages in Order of Newest -> Oldest
     *
     * @param m Messageto compare dates with
     * @return int 1, 0, -1 newer, equal, older
     */
    public int compareTo(Message m) {
        if (getCreated() == null || m.getCreated() == null)
            return 0;
        return getCreated().compareTo(m.getCreated()) * -1;
    }

    /**
     * Get Message String
     *
     * @return String message age
     */
    public String getMessage(){
        return message;
    }

    /**
     * Get Message owner
     *
     * @return User message owner object
     */
    public User getOwner(){
        return owner;
    }

    /**
     * formatted Message string to send to output in format:
     * {username} - {message} (2 weeks 1 days ago )
     *
     * @return String formatted message String with user, message and created date
     */
    public String toString() {
        return owner.getUsername() + " - " + message + " " + messageAge();
    }
}
