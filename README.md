# Spray/Akka/MongoDB/SBT11 project

[g8](http://github.com/n8han/giter8) This is a bare bones REST template implemented using the [Spray](http://spray.cc) framework on top of Akka.  Persistence is handled with [MongoDB](http://mongodb.com).

## Usage

Install giter8 (g8) - [readme](http://github.com/n8han/giter8#readme) for more information.

Install SBT 0.11.X - [Setup](https://github.com/harrah/xsbt/wiki/Setup) for more information.

Install MongoDB - [Setup](http://www.mongodb.org) for more information.

In a shell, run the following:

    g8 ctcarrier/spray-rest-sbt10.g8
    cd <name-of-app>
    sbt
    > update
    > jetty-run
    
You should be able to browse to a access a JSON body for your resource with 'curl -vv localhost:8080/$resourceName$'

