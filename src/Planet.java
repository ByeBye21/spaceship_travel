/** 
* 
* @author YOUNES RAHEBI | younes.rahebi@ogr.sakarya.edu.tr
* @since 26/04/2025
* <p> 
* Bir gezegeni ve üzerindeki zamanı temsil eder.
* Zaman ilerledikçe tarih değişir ve gezegenin nüfusu hesaplanabilir.
* </p> 
*/ 

import java.util.List;

//gezegen sınıfı: gezegenleri yönetir
public class Planet {
    private String name;
    private Time time;
    private int dayHour;

    public Planet(String name, int dayHour, String date) {
        this.name = name;
        this.dayHour = dayHour;
        this.time = new Time(date, dayHour);
    }

    //zamanı bir saat ilerletir
    public void advanceTime() {
        time.addHours(1);
    }

    public String getDate() {
        return time.getDate();
    }

    public String getName() {
        return name;
    }

    public Time getTime() {
        return time;
    }

    //gezegenin mevcut nüfusunu hesaplar
    public int getPopulation(List<Spaceship> spaceships, List<Person> people) {
        int population = 0;

        for (Spaceship ship : spaceships) {
            //imha olmuşlar atla
            if (ship.isDestroyed()) continue;

            //yoldaysa atla
            if (ship.getStatus().equals("Yolda")) continue;

            //bekliyor veya varıldı: gemenin konumu bu gezegense yolcuları say
            String curr = ship.getCurrentPlanet();
            if (curr != null && curr.equals(this.name)) {
                for (Person p : ship.getPassengers()) {
                    if (p.isAlive()) {
                        population++;
                    }
                }
            }
        }

        return population;
    }

}
