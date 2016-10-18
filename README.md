## java-client

A console-based chat client & server, built using strong TDD throughout. It uses Java 8, JUnit and Hamcrest matchers, but has not other dependencies.

This is a teaching application designed to show how real-world applications can be structured. There are a number of areas in which the application can be extended, and you are encouraged to do so in your own fork. Suggestions for improvements are listed below.


## Prerequisities

* JDK 8
* Maven

## How to build


Run `mvn package` to compile, run tests and package the application.
The jar file will be output in `target` and named `chat-1.0.jar`.


## Starting a chat server

1. First build the application and then change to the `target` directory. If you are in the project root then you can do this with `cd target`.

2. Run the following command.

```
java -jar chat-1.0.jar server <port number>
```

If you do not specify a port number the system uses a default of 3000. If the port is already in use, you will see an "Address already in use" error.

## Joining the chat room

1. First build the application and then change to the `target` directory. If you are in the project root then you can do this with `cd target`.

2. Run the following command.

```
java -jar chat-1.0.jar client <address> <port number> <username>
```
If the name is already in use you will see an error.

## Suggested improvements

### Simple

* Helpful stdout messages for both client and server
* Alert users when someone registers
* Disconnect message and alert user leaves
* Ping message that auto-disconnects after a period of silence

### More involved

* Add support for multiple rooms
* Add a moderator role that has the ability to kick users
* Add support for password-protected user accounts
* Allow communication via websockets with a web frontend
* Client command to list all users

## Contributing

Contributions (of all forms) are most welcome. Please submit pull requests in the normal fashion or contact me directly.


