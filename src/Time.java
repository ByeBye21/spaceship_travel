/** 
 * @author YOUNES RAHEBI | younes.rahebi@ogr.sakarya.edu.tr
 * @since 26/04/2025
 * <p> 
 * Tarih ve saat hesaplamaları için kullanılır.
 * Saat ilerletme ve tarih karşılaştırma işlemlerini sağlar.
 * </p> 
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

//zaman sınıfı: gezegendeki zamanı yönetir
public class Time {
    private int hour;
    private int dayHour; //bir gün kaç saat
    private LocalDate date;

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("[d.M.yyyy][dd.MM.yyyy][dd.M.yyyy][d.MM.yyyy]")
            .toFormatter();
    
    public Time(String dateStr, int dayHour) {
        this.date = LocalDate.parse(dateStr, FORMATTER);
        this.hour = 0;
        this.dayHour = dayHour;
    }

    public Time(Time other) {
        this.date = other.date;
        this.hour = other.hour;
        this.dayHour = other.dayHour;
    }

    //saat ekleyerek zamanı ilerletir
    public void addHours(int hours) {
        this.hour += hours;
        while (this.hour >= dayHour) {
            this.hour -= dayHour;
            this.date = this.date.plusDays(1);
        }
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String getDateTime() {
        return String.format("%s - %02d:00", date.format(FORMATTER), hour);
    }

    //tarih karşılaştırması yapar (saat hariç)
    public boolean equalsDate(Time other) {
        return this.date.isEqual(other.date);
    }

    public Time addHoursToNew(int hours) {
        Time newTime = new Time(this);
        newTime.addHours(hours);
        return newTime;
    }

    public int getDayHour() {
        return dayHour;
    }

    public LocalDate getLocalDate() {
        return date;
    }

    public int getYear() {
        return date.getYear();
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public int getHour() {
        return hour;
    }
}