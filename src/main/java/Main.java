
import song.SongLibrary;
import song.SongParameters;

import web.WebManager;


import java.io.IOException;
import java.util.Scanner;
import java.util.Set;



public class Main {

    public static void printSetValues(Set<String> set) {
        int counter = 0;
        for (String string : set) {
            if (counter > 40) {
                counter = 0;
                System.out.println(string + ", ");
            }
            else {
                System.out.print(string + ", ");
                counter += string.length();
            }
        }
    }
    
    public static int validateNumber(Scanner s) {
        boolean validNumber = false;
        int num = 0;

        while(!validNumber) {
            try {
                num  = s.nextInt();
                validNumber = true;
            }
            catch(RuntimeException e) {
                System.out.println("Input needs to be a valid number");
                s.nextLine();
            }
        }
        s.nextLine();
        
        return num;
    }

    public static void downloadGenreSongs(Scanner in, SongLibrary lib) throws IOException {
        String genre;
        String period;
        int qty;

        Set<String> genres = WebManager.getCategories();

        System.out.println("Available genres: ");

        printSetValues(genres);

        System.out.println();

        System.out.print("Enter the desired genre: ");

        genre = in.nextLine();

        while (!genres.contains(genre)) {
            System.out.println("Genre not valid. Please enter a valid genre.");
            genre = in.nextLine();
        }

        System.out.println("----------------------------------------------------------");

        System.out.print("Enter how many songs you wish to download (<= 0 means all of them): ");

        qty = validateNumber(in);

        System.out.println("------------------------------------------------------");

        Set<String> periods = WebManager.getPeriods();

        System.out.println("Available periods: ");

        printSetValues(periods);

        System.out.println();

        System.out.print("Enter the desired period: ");

        period = in.nextLine();

        while (!periods.contains(period)) {
            System.out.println("Period not valid. Please enter a valid period.");
            period = in.nextLine();
        }

        System.out.println("----------------------------------------------------------");

        WebManager b = new WebManager();

        SongParameters parameters = new SongParameters.Builder().setGenre(genre).setPeriod(period).setSortOrder("top").build();

        b.fetchSongs(parameters,qty);

        b.downloadAndAddToLibrary(lib);

    }

    public static void downloadBookmarkedSongs(Scanner in, SongLibrary lib) throws IOException {
        int qty;
        String username;

        System.out.print("Enter your username: ");

        username = in.nextLine();

        System.out.print("Enter how many songs you wish to download (<= 0 means all of them): ");

        qty = validateNumber(in);

        System.out.println("------------------------------------------------------");

        WebManager b = new WebManager();

        SongParameters parameters = new SongParameters.Builder().setBookmark(username).setSortOrder("new").build();

        b.fetchSongs(parameters,qty);

        b.downloadAndAddToLibrary(lib);
    }

    public static void main(String[] args) throws IOException {
        SongLibrary lib = new SongLibrary();
        Scanner in = new Scanner(System.in);

        System.out.print("Enter 1 to download bookmarked songs or 2 to download songs by genre: ");
        int option = validateNumber(in);

        if (option == 2) {
            downloadGenreSongs(in, lib);
        }
        else {
            downloadBookmarkedSongs(in, lib);
        }

    }


}
