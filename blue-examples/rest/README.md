# Example: REST

This is a simple example of a simple REST oriented web service.

## API

### add(Int,Int): Int

    /add?a=[Int]&b=[Int]

Adds two integers `a` and `b` and returns an integer.

### sub(Int,Int): Int

    /sub?a=[Int]&b=[Int]

The difference between two integers `a` and `b` and returns an integer.

## Run

1. Start sbt from `/blue` directory. 

        $ cd blue
        $ sbt
        >

2. Switch to the REST example:

        > project ex-rest
        [info] Set current project to ex-rest (in build file:[...]/blue/)

3. Run

        > run
        [info] ...
        [info] Running main

4. Try

        $ curl "http://localhost:8080/add?a=1&b=2"
        3

FIN.