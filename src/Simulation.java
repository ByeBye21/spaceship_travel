/** 
* 
* @author YOUNES RAHEBI | younes.rahebi@ogr.sakarya.edu.tr
* @since 26/04/2025
* <p> 
* Tüm simülasyonu yöneten ana sınıftır.
* Gezegenlerin zamanını ve uzay araçlarının hareketini her saat başı günceller.
* </p> 
*/ 

import java.io.IOException;
import java.util.*;

//simülasyonu yöneten ana sınıf
public class Simulation {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<Person> people = FileReaderUtil.readPeople("Kisiler.txt");
        List<Planet> planets = FileReaderUtil.readPlanets("Gezegenler.txt");
        List<Spaceship> spaceships = FileReaderUtil.readSpaceships("Araclar.txt", people, planets);

        boolean running = true;

        while (running) {
        	//her gezegende zamanı ilerlet
            for (Planet planet : planets) {
                planet.advanceTime();
            }
            
            //her uzay aracının durumunu güncelle
            for (Spaceship spaceship : spaceships) {
                Planet currentPlanet = null;
                for (Planet planet : planets) {
                    if (spaceship.getCurrentPlanet() != null &&
                        planet.getName().equals(spaceship.getCurrentPlanet())) {
                        currentPlanet = planet;
                        break;
                    }
                }
                spaceship.progress(currentPlanet);
            }

            //ekranı temizle
            try {
                if (System.getProperty("os.name").contains("Windows")) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } else {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                }
            } catch (IOException | InterruptedException ex) {}

            //gezegen bilgilerini yazdır
            System.out.println("Gezegenler:");
            System.out.printf("%-20s", "");
            for (Planet planet : planets) {
            	System.out.printf("%-18s", "---" + planet.getName() + "---");
            }
            System.out.println();
            System.out.printf("%-20s", "Tarih");
            for (Planet planet : planets) {
                System.out.printf("%-18s", planet.getDate());
            }
            System.out.println();
            System.out.printf("%-20s", "Nufus");
            for (Planet planet : planets) {
                System.out.printf("%-18d", planet.getPopulation(spaceships, people));
            }
            System.out.println("\n");

            //uzay aracı bilgilerini yazdır
            System.out.println("Uzay Araclari:");
            System.out.printf("%-15s%-15s%-15s%-15s%-25s%-20s%n",
                    "Arac Adi", "Durum", "Cikis", "Varis", "Hedefe Kalan Saat", "Varis Tarihi");

            for (Spaceship spaceship : spaceships) {
                String status = spaceship.getStatus();
                String departure = spaceship.getDeparturePlanet();
                String destination = spaceship.getDestinationPlanet();
                String remainingHours = spaceship.isDestroyed() ? "--" : String.valueOf(spaceship.getRemainingHours());
                String arrival = spaceship.isDestroyed() ? "---" : spaceship.getarrivalDate().getDate();

                System.out.printf("%-15s%-15s%-15s%-15s%-25s%-20s%n",
                    spaceship.getName(),
                    status,
                    departure,
                    destination,
                    remainingHours,
                    arrival);
            }
            //simülasyonun bitip bitmediğini kontrol et
            running = spaceships.stream().anyMatch(s -> !s.hasArrived() && !s.isDestroyed());
        }
        System.out.println("\nTUM UZAY ARACLARININ HEDEFLERINE VARDI VEYA IMHA OLDU.");
    }
}
