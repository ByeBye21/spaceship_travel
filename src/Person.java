/** 
* 
* @author YOUNES RAHEBI | younes.rahebi@ogr.sakarya.edu.tr
* @since 26/04/2025
* <p> 
* Bir kişiyi temsil eder. Kişinin yaşı, yaşam süresi ve bindiği uzay aracı bilgilerini içerir.
* Zaman geçtikçe yaşam süresi azalır.
* </p> 
*/ 

//kişi sınıfı: insanları temsil eder
public class Person {
    private String name;
    private int age;
    private int remainingLife;
    private String spaceshipName;

    public Person(String name, int age, int remainingLife, String spaceshipName) {
        this.name = name;
        this.age = age;
        this.remainingLife = remainingLife;
        this.spaceshipName = spaceshipName;
    }

    //zaman geçtikçe yaşam süresi azalır
    public void passTime() {
        if (remainingLife > 0) remainingLife--;
    }

    //kişi hala hayatta mı?
    public boolean isAlive() {
        return remainingLife > 0;
    }

    public String getSpaceshipName() {
        return spaceshipName;
    }
}