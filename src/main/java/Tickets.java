import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRulesException;
import java.util.ArrayList;

@Data
public class Tickets
{
    private String origin;
    @JsonProperty("origin_name")
    private String originName;
    private String destination;
    @JsonProperty("destination_name")
    private String destinationName;
    @JsonProperty("departure_date")
    private String departureDate;
    @JsonProperty("departure_time")
    private String departureTime;
    @JsonProperty("arrival_date")
    private String arrivalDate;
    @JsonProperty("arrival_time")
    private String arrivalTime;
    private String carrier;
    int stops;
    int price;

    public ZonedDateTime getDate(String date, String time, String airport) throws ZoneRulesException  {
            ZoneId zoneId;
            switch (airport) {
                case "VVO":
                    zoneId = ZoneId.ofOffset("UTC", ZoneOffset.of("+10"));
                    break;
                case "TLV":
                    zoneId = ZoneId.ofOffset("UTC", ZoneOffset.of("+03"));
                    break;
                default:
                    throw new ZoneRulesException("Неизвестный аэропорт : " + airport);
            }

        ArrayList<Integer> fullDate = getFullDate(date, time);
        return ZonedDateTime.of(2000 + fullDate.get(2), fullDate.get(1), fullDate.get(0),
                fullDate.get(3), fullDate.get(4), 0, 0, zoneId);
    }

    private ArrayList<Integer> getFullDate (String date, String time) {
        ArrayList<Integer> fullDate = new ArrayList<>();
        String[] splitDate = date.split("\\.");
        String[] splitTime = time.split(":");
        for(String string : splitDate) {
            fullDate.add(Integer.parseInt(string));
        }
        for(String string : splitTime) {
            fullDate.add(Integer.parseInt(string));
        }
        return fullDate;
    }
}
