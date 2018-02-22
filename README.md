# jackson-databind-jdk9-issue
Tiny project that tries to reproduce an issue found on Jackson Databind.

## The issue

For some reason Jackson is failing to bind objects that have constructors with parameters when used with JDK 9.

There was a very similar issue ([393](https://github.com/FasterXML/jackson-core/issues/393)) regarding Lombok annotations where Jackson would raise the same exception it raises 
in the cases presented here. 

At the aforementioned issue's discussions, someone suggested disabling the `INFER_CREATOR_FROM_CONSTRUCTOR_PROPERTIES` feature from the Mapper. 

The given suggestion was applied here but the problem persists.  

There are four scenarios reproduced in this repository:

__Pojos:__

- A Pojo class with constructor arguments (__FAIL__)
- A Pojo class with empty constructor (__PASS__)

__Lombok generated constructors:__

- A Lombok based class with constructor arguments (__FAIL__)
- A Lombok based class with empty constructor (__PASS__)

When running the tests, the following output is presented:

```sh

> Task :test

ObjectMapperTest > lombok_all_args_fails FAILED
    com.fasterxml.jackson.databind.exc.InvalidDefinitionException at ObjectMapperTest.java:46

ObjectMapperTest > pojo_all_args_fails FAILED
    com.fasterxml.jackson.databind.exc.InvalidDefinitionException at ObjectMapperTest.java:55

4 tests completed, 2 failed

``` 

## Reproducing

```sh
./gradlew test --info
```


## Information

__JDK Version:__

```sh

$ java -version

java version "9.0.4"
Java(TM) SE Runtime Environment (build 9.0.4+11)
Java HotSpot(TM) 64-Bit Server VM (build 9.0.4+11, mixed mode)
```

__Gradle version:__

```sh

------------------------------------------------------------
Gradle 4.2.1
------------------------------------------------------------

Build time:   2017-10-02 15:36:21 UTC
Revision:     a88ebd6be7840c2e59ae4782eb0f27fbe3405ddf

Groovy:       2.4.12
Ant:          Apache Ant(TM) version 1.9.6 compiled on June 29 2015
JVM:          9.0.4 (Oracle Corporation 9.0.4+11)
OS:           Linux 4.13.0-32-generic amd64

```


