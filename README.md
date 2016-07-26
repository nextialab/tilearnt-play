## Today I Learnt Play! 2 Project

Today I Learnt Play! 2 Project

### Setup

This project depends on Scala and SBT, and connects to a typical MySQL database. It also requires a database named ```tilearnt```, a user ```tilearnt``` with a password also ```tilearnt```. You can configure this info in conf/application.conf

### Compiling

Run

    sbt

to enter in ```sbt``` mode. On the first launch it will read the project configuration, and fetch and install any required dependency. In ```sbt``` mode you should run:

    compile

to compile the source code and, if no error is found, run the server with:

    run

This will load a server listening in port 9000. To stop the server just press ```Ctrl + D```.