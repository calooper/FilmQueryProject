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
			// TODO Auto-generated catch block
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
		System.out.println("+-----------------------------------+");
		int menuChoice = scanner.nextInt();

		while (keepGoing) {
			switch (menuChoice) {
			case 1:
				Film film = null;
				System.out.println("Enter a film ID please");
				int filmID = scanner.nextInt();
				film = filmByID(filmID);
				if (film == null) {
					System.out.println("Sorry that is not a film ID");
					startUserInterface(scanner);
					break;
				} else
					System.out.println(film);
				startUserInterface(scanner);
				break;

			case 2:
				int id = 0;
				int count = 0;
				List<Film> films;
				System.out.println("Please enter a keyword to search for a movie");
				String keyword = scanner.next();
				films = searchFilm(keyword);
				if (films.isEmpty()) {
					System.out.println("There are no films matching that keyword");
					startUserInterface(scanner);
				} else

					for (Film filmPrint : films) {

						if (filmPrint.getId() == id) {

							continue;
							
						} else {
							System.out.println(filmPrint);
							count++;
							id = filmPrint.getId();

						}

					}
				if (count != 0)
					System.out.println("-----------------------------------");
					System.out.println("There's " + count + " film(s) matching that keyword");
				startUserInterface(scanner);
				break;

			case 3:
				System.out.println("Exiting...");
				keepGoing = false;
				break;

			default:
				continue;
			}
		}
	}

	private Film filmByID(int filmID) {

		DatabaseAccessorObject dbo = new DatabaseAccessorObject();
		Film film = null;

		try {
			film = dbo.findFilmById(filmID);
			if (film == null) {

			}
			return film;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;

	}

	private List<Film> searchFilm(String keyword) {

		List<Film> films = null;
		DatabaseAccessorObject dbo = new DatabaseAccessorObject();

		try {
			films = dbo.findFilmByKeyword(keyword);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return films;
	}
}
