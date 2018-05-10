# HList-based record implemention for Dotty

Toy implementation of ordered records.

Inspired by [Shapeless](https://github.com/milessabin/shapeless/)

## Compile

Make sure to clone the fork of Dotty: <https://github.com/obkson/dotty/tree/dotty-records>

Clone, compile and package the dotty-records lib: <https://github.com/obkson/dotty-records>

Run the `install_deps` script to install unmanaged dependencies into local repo.

To get an executable jar-file with all dependencies:

```
mvn clean compile assembly:single
```


## Run

```
java -jar target/dotty-hlist_0.8-0.1-jar-with-dependencies.jar 
```
