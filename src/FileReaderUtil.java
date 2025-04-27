/** 
* 
* @author YOUNES RAHEBI | younes.rahebi@ogr.sakarya.edu.tr
* @since 26/04/2025
* <p> 
* Dosyalardan kişi, gezegen ve uzay aracı bilgilerini okuyan yardımcı sınıftır.
* Verileri satır satır okuyarak ilgili nesneleri oluşturur.
* </p> 
*/ 

import java.io.*;
import java.util.*;

//dosya okuma sınıfı: verileri dosyalardan okur
public class FileReaderUtil {
    public static List<Person> readPeople(String fileName) throws IOException {
        List<Person> people = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("#");
            people.add(new Person(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), parts[3]));
        }
        br.close();
        return people;
    }

    public static List<Spaceship> readSpaceships(String fileName, List<Person> people, List<Planet> planets) throws IOException {
        List<Spaceship> spaceships = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("#");
            String spaceshipName = parts[0];
            String departurePlanetName = parts[1];
            String destinationPlanetName = parts[2];
            String departureDateStr = parts[3];
            int distance = Integer.parseInt(parts[4]);

            //ilgili yolcuları listele
            List<Person> passengers = new ArrayList<>();
            for (Person person : people) {
                if (person.getSpaceshipName().equals(spaceshipName) && !person.getSpaceshipName().equals("-")) {
                    passengers.add(person);
                }
            }

            //kalkış ve varış gezegenlerini eşleştir
            Planet departure = planets.stream()
                    .filter(p -> p.getName().equals(departurePlanetName))
                    .findFirst().orElse(null);

            Planet destination = planets.stream()
                    .filter(p -> p.getName().equals(destinationPlanetName))
                    .findFirst().orElse(null);

            //gezegenler bulunduysa uzay aracı oluştur
            if (departure != null && destination != null) {
                Time departureTime = new Time(departureDateStr, departure.getTime().getDayHour());
                Spaceship spaceship = new Spaceship(
                        spaceshipName,
                        departurePlanetName,
                        destinationPlanetName,
                        departureTime,
                        distance,
                        passengers,
                        departure,
                        destination
                );
                spaceships.add(spaceship);
            } else {
                System.err.println("Hata: Uzay aracı '" + spaceshipName + "' için kalkış (" + 
                    departurePlanetName + ") veya varış (" + destinationPlanetName + ") gezegeni bulunamadı.");
            }
        }
        br.close();
        return spaceships;
    }

    public static List<Planet> readPlanets(String fileName) throws IOException {
        List<Planet> planets = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("#");
            planets.add(new Planet(parts[0], Integer.parseInt(parts[1]), parts[2]));
        }
        br.close();
        return planets;
    }
}
