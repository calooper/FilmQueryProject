package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		try {
			startUserInterface(input);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		input.close();
	}

	private void startUserInterface(Scanner scanner) throws SQLException {

		boolean keepGoing = true;

		System.out.println("+-----------------------------------+");
		System.out.println("   Please choose an option:");
		System.out.println("1. Look up a film by its id ");
		System.out.println("2. Look up a film by a search keyword");
		System.out.println("3. Exit the application. ");
		int menuChoice = scanner.nextInt();

		while (keepGoing) {
			switch (menuChoice) {
			case 1:

				filmByID(scanner);
				break;

			case 2:

				searchFilm(scanner);
				break; 
				
			case 3:
				System.out.println("Exiting...");
				keepGoing = false;
				System.exit(1);
				break;

			default:
				continue;
			}
		}
	}

	private void filmByID(Scanner scanner) throws SQLException {

		DatabaseAccessorObject dbo = new DatabaseAccessorObject();
		Film film = null;

		System.out.println("Enter a film ID please");
		int filmID = scanner.nextInt();

		try {
			film = dbo.findFilmById(filmID);
			if (film == null) {
				System.out.println("Sorry that is not an Id in the database");

			} else
				System.out.println(film);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		startUserInterface(scanner);

	}

	private void searchFilm(Scanner scanner) throws SQLException {
		

		DatabaseAccessorObject dbo = new DatabaseAccessorObject();

		int id = 0;
		List<Film> films = null;
		System.out.println("Please enter a keyword to search for a movie");
		String keyword = scanner.next();

		try {
			films = dbo.findFilmByKeyword(keyword);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (films.isEmpty()) {
			System.out.println("There are no films matching that keyword");
		} else

			for (Film filmPrint : films) {

				if (filmPrint.getId() == id) {

					continue;

				} else {
					System.out.println(filmPrint);
					id = filmPrint.getId();

				}

			}
		if (films.size() != 0) {
			System.out.println("----------------------------------------------");
		System.out.println("There's " + films.size() + " film(s) matching keyword: " + keyword);
		startUserInterface(scanner);
		
		}
		else
			
		startUserInterface(scanner);
		
		
		


	}
}
