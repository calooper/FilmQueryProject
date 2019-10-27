package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	@Override
	public Film findFilmById(int filmId) throws SQLException {

		List<Actor> actors = findActorsByFilmId(filmId);
		Film film = null;

		String user = "student";
		String pass = "student";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT film.id, title, description, release_year, language_id, rental_duration, "
					+ "length,replacement_cost, rating FROM film WHERE id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet filmResult = stmt.executeQuery();
			if (filmResult.next()) {

				Integer id = filmResult.getInt("id"); // a set.id needs to construct a list of actors
				String title = filmResult.getString("title"); // a set.id needs to construct a list of actors
				String description = filmResult.getString("description");
				Integer realeseYear = filmResult.getInt("release_year");
				int languageId = filmResult.getInt("language_id");
				String language = getLanguage(languageId);
				int rentalDuration = filmResult.getInt("rental_duration");
				int length = filmResult.getInt("length");
				Double replacementCost = filmResult.getDouble("replacement_cost");
				String rating = filmResult.getString("rating");

				film = new Film(id, title, description, realeseYear, language, languageId, rentalDuration, length,
						replacementCost, rating, actors);

			}

			filmResult.close();
			stmt.close();
			conn.close();
		} catch (

		SQLException e) {
			e.printStackTrace();
		}

		return film;

	}

	@Override
	public Actor findActorById(int actorId) throws SQLException {

		String user = "student";
		String pass = "student";

		Actor actor = null;

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet actorResult = stmt.executeQuery();
			if (actorResult.next()) {
				actor = new Actor();

				actor.setId(actorResult.getInt("id"));
				actor.setFirstName(actorResult.getString("first_name"));
				actor.setLastName(actorResult.getString("last_name"));
			}

			actorResult.close();
			stmt.close();
			conn.close();
		} catch (

		SQLException e) {
			e.printStackTrace();
		}

		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {

		String user = "student";
		String pass = "student";

		List<Actor> actorList = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT * " + " FROM Actor JOIN film_actor  ON actor.id = film_actor.actor_id "
					+ " WHERE id is not null and film_id = ? ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");

				Actor actor = new Actor(id, firstName, lastName);
				actorList.add(actor);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actorList;
	}

	@Override
	public List<Film> findFilmByKeyword(String keyword) throws SQLException {

		List<Actor> actors;
		Film film = null;
		List<Film> films = new ArrayList<>();

		String user = "student";
		String pass = "student";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT * FROM film WHERE description like ? OR title like ? order by id";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");
			ResultSet filmResult = stmt.executeQuery();
			while (filmResult.next()) {

				Integer id = filmResult.getInt("id"); // a set.id needs to construct a list of actors
				actors = findActorsByFilmId(id);
				String title = filmResult.getString("title"); // a set.id needs to construct a list of actors
				String description = filmResult.getString("description");
				Integer realeseYear = filmResult.getInt("release_year");
				int languageId = filmResult.getInt("language_id");
				String language = getLanguage(languageId);
				int rentalDuration = filmResult.getInt("rental_duration");
				int length = filmResult.getInt("length");
				Double replacementCost = filmResult.getDouble("replacement_cost");
				String rating = filmResult.getString("rating");

				film = new Film(id, title, description, realeseYear, language, languageId, rentalDuration, length,
						replacementCost, rating, actors);

				films.add(film);

			}

			filmResult.close();
			stmt.close();
			conn.close();
		} catch (

		SQLException e) {
			e.printStackTrace();
		}

		return films;

	}

//
	public String getLanguage(int languageId) throws SQLException {
		String language = null;

		String user = "student";
		String pass = "student";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT  name, id FROM language where id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, languageId);

			ResultSet filmResult = stmt.executeQuery();
			if (filmResult.next()) {

				language = filmResult.getString("name");

			}

			filmResult.close();
			stmt.close();
			conn.close();
		} catch (

		SQLException e) {
			e.printStackTrace();
		}
		return language;
	}
}
