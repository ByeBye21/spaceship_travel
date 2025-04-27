/** 
* 
* @author YOUNES RAHEBI | younes.rahebi@ogr.sakarya.edu.tr
* @since 26/04/2025
* <p> 
* Bir uzay aracını, yolculuk bilgilerini ve yolcularını yönetir.
* Hareket ve imha durumları burada takip edilir.
* </p> 
*/ 

import java.util.List;
import java.time.temporal.ChronoUnit;

//uzay aracı sınıfı: uzay araçlarını yönetir
public class Spaceship {
    private String name;
    private String departurePlanet;
    private String destinationPlanet;
    private Time departureDate;
    private int distance;
    private int remainingHours;
    private List<Person> passengers;
    private boolean destroyed;
    private boolean inTransit;
    private String currentPlanet;
    private Time arrivalDate;

    public Spaceship(String name, String departurePlanet, String destinationPlanet, Time departureDate, int distance, List<Person> passengers, Planet departure, Planet destination) {
        this.name = name;
        this.departurePlanet = departurePlanet;
        this.destinationPlanet = destinationPlanet;
        this.departureDate = departureDate;
        this.distance = distance;
        this.remainingHours = distance;
        this.passengers = passengers;
        this.destroyed = false;
        this.inTransit = false;
        this.arrivalDate = getArrivalDate(departure, destination);
        this.currentPlanet = departurePlanet;
    }

    //uzay aracının durumunu ilerletir
    public void progress(Planet currentPlanet) {
        if (destroyed) return;

        //kalkış zamanı geldiyse yola çıkar
        if (!inTransit && currentPlanet != null && currentPlanet.getName().equals(departurePlanet)) {
            if (currentPlanet.getTime().equalsDate(departureDate)) {
                inTransit = true;
                this.currentPlanet = null;
            }
        }

        //yoldaysa kalan süreyi azalt
        if (inTransit && remainingHours > 0) {
            remainingHours--;
        }

        //yolcuların yaşam süresini kontrol et
        passengers.forEach(Person::passTime);
        passengers.removeIf(person -> !person.isAlive());

        if (passengers.isEmpty()) {
            destroyed = true;
        }

        //hedefe ulaşıldıysa
        if (remainingHours == 0 && inTransit) {
            inTransit = false;
            this.currentPlanet = destinationPlanet;
        }
    }

    public boolean hasArrived() {
        return remainingHours == 0 && !inTransit;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public String getStatus() {
        if (destroyed) return "IMHA";
        if (inTransit) return "Yolda";
        if (hasArrived()) return "Vardi";
        return "Bekliyor";
    }

    public int getRemainingHours() {
        return remainingHours;
    }

    public String getName() {
        return name;
    }

    public String getDeparturePlanet() {
        return departurePlanet;
    }

    public String getDestinationPlanet() {
        return destinationPlanet;
    }

    public String getCurrentPlanet() {
        return currentPlanet;
    }

    public List<Person> getPassengers() {
        return passengers;
    }
    
    public Time getarrivalDate() {
        return arrivalDate;
    }

    //varış tarihini hesaplar
    public Time getArrivalDate(Planet departure, Planet destination) {
        if (destroyed) return null;

        //çıkış tarihi: çıkış gezegeninin zamanına göre
        Time departureTime = new Time(departureDate.getDate(), departure.getTime().getDayHour());

        //şu anki simülasyon zamanı: çıkış gezegenindeki zaman
        Time currentDeparturePlanetTime = departure.getTime();

        //şu anki zamandan çıkış tarihine kadar kaç saat var?
        int hoursUntilDeparture = calculateHoursBetween(currentDeparturePlanetTime, departureTime, departure.getTime().getDayHour());

        //bu süre + mesafe kadar saat geçince, varış zamanı olur
        int totalTravelHours = Math.max(0, hoursUntilDeparture) + distance;

        //bu süreyi varış gezegeninin zamanına göre ekleyerek varış tarihi hesapla
        Time arrivalTime = new Time(destination.getTime().getDate(), destination.getTime().getDayHour());
        arrivalTime.addHours(totalTravelHours);
        return arrivalTime;
    }
    private int calculateHoursBetween(Time from, Time to, int dayHour) {
        long daysBetween = ChronoUnit.DAYS.between(from.getLocalDate(), to.getLocalDate());
        int hourDifference = to.getHour() - from.getHour();
        return (int) (daysBetween * dayHour + hourDifference);
    }
}
