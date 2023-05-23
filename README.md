# scrabble-resolver

Once you have built and run the application you can go to http://localhost:8080/swagger-ui.html and see some of the documentation to start using the service, you can even try it out there on the specified option by providing the set of letters.

I have limited the service to accept up to 7 letters as the Scrabble rules specify, it is validated to receive only letters and a length no greater than 7.

No special tools are needed, it runs with Java 11 and maven, it is a simple SpringBoot application.

What it takes more time in this application is to filter all the valid words out of the Dictionary provided in Dictionary.txt file, it has almost 110,000 words in it, it also has one line with no word (just a line break) and some other words with apostrophes, I am not sure if they are valid for Scrabble or not.

I know permutations are not efficient, but I tried to use a Tree and it was duplicating letters from the input to create word combinations. The algorithm gets all the permutations very fast, but as I said what it takes time is to filter them against the provided dictionary if you provide 7 letters.

The dictionary provided is already integrated in this app within the Resources folder as a text file so no need to provide it again.

I have created some tests to check the code, it only has tests for the service as the service uses all other methods and I didn’t create separated tests for each method as time was running out. 

# Requirements:
Requires Maven 3 or newer and Java JDK 8 or newer.

To build:
mvn clean package

To run tests and coverage report:
mvn clean verify

To run checkstyle report:
mvn checkstyle:checkstyle