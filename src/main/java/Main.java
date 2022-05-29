import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

public class Main
{
    static final String FILE = "tickets.json";
    static final int PERCENTILE = 90;

    public static void main(String[] args) throws IOException {
        InputStream inputStream = Main.class.getResourceAsStream(FILE);
        ObjectMapper mapper = new ObjectMapper();
        AviaTickets aviaTickets = mapper.readValue(inputStream, AviaTickets.class);

        ArrayList<Long> listFlight = new ArrayList<>();
        long amount = 0L;
        for(Tickets tik : aviaTickets.getTickets()) {
            ZonedDateTime timeDeparture= tik.getDate(tik.getDepartureDate(), tik.getDepartureTime(), tik.getOrigin());
            ZonedDateTime timeArrival = tik.getDate(tik.getArrivalDate(), tik.getArrivalTime(), tik.getDestination());
            long difference = timeDeparture.until(timeArrival, ChronoUnit.MINUTES);
            listFlight.add(difference);
            amount += difference;
        }
        double average = (double) amount / aviaTickets.getTickets().size();
        System.out.println("Average flight time between Vladivostok and Tel Aviv = " + average + " min.");

        Collections.sort(listFlight);
        Collections.reverse(listFlight);
        double rang = ((double) PERCENTILE/100) * aviaTickets.getTickets().size();
        System.out.println("90th percentile of flight time between Vladivostok and Tel Aviv = "
                + rang + " / " + listFlight.get((int)rang-1) + " min.");
    }
}
