### OVERVIEW

This project is a command-line application that uses ORM with Java Database
Connectivity to retrieve information from a database


The application prompts the user to search for a film by id or keyword. Each film in the
database has a unique key called id. The the application searches the database using the
unique and returns a film with its other attributes - including actors in the film. If
the database yields no results, the user will be informed that there was no id
matching a film in the database and prompted to the main menu.


If the user elects to search via keyword, the database retrieves any films
that have that keyword in the description or title. If there are no films matching
the keyword, the user will be informed and then prompted to select another option.


### Technologies
The JDBC code is encapsulated in methods in a class called DataAccessorObject.
Each of these methods connects to the database and executes a prepared statement
from a string SQL query. The information from the database is used to create
Film objects and Actor objects. Film objects contain a list of actor objects.



### Lessons learned
This project was a great experience getting to work with a database. I did have
some trouble with my SQL query returning too many results from my joiner table,
but I eventually figured out that the problem was in my ON clause.
