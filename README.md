# Jackson-databind JDK9 issue

When used with JDK 9, Jackson's `ObjectMapper` launches a `com.fasterxml.jackson.databind.exc.InvalidDefinitionException`
while trying to read Json content into a class that declares constructors with arguments. It works when the same is 
attempted with a class that declares a constructor with no arguments.  

The issue [393](https://github.com/FasterXML/jackson-core/issues/393) shows a very similar problem. The only difference 
is that in such issue the problem was related to Lombok annotations whereas in the current scenario the problem happens 
in __both Pojos and Lombok annotated classes.__  
 

At the aforementioned issue's discussions [someone suggested disabling the `INFER_CREATOR_FROM_CONSTRUCTOR_PROPERTIES`
feature](https://github.com/FasterXML/jackson-core/issues/393#issuecomment-321173529) from the Mapper but that did not solve the current
problem and the exception is still being thrown.

Still in that discussion, the `@JsonCreator` annotation [was mentioned as a possible way to solve the
problem](https://github.com/FasterXML/jackson-core/issues/393#issuecomment-321091757). Such annotation was added (introduced at
3242f6dd9fcd7d020179d5d05cbe9d070db83fdc, it's also possible to reproduce the cases where the annotation is absent by reverting to a
previous commit) on the cases with constructor arguments but the problem still persists (with or without `@JsonCreator`).

## Test Scenarios

There are four scenarios reproduced in this repository:

__Pojos:__

- A Pojo class with constructor arguments (__FAIL__)
- A Pojo class with empty constructor (__PASS__)

__Lombok generated constructors:__

- A Lombok based class with constructor arguments (__FAIL__)
- A Lombok based class with empty constructor (__PASS__)

Given scenarios were run with and without applying the `@JsonCreator` annotation to the classes.

When running the tests, the following output is presented:


### When `@JsonCreator` is applied:

```sh

ObjectMapperTest > lombok_all_args_fails FAILED
    com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Invalid type definition for type `lombok.PersonAllArgs`: Argument #0 has
no property name, is not Injectable: can not use as Creator [constructor for lombok.PersonAllArgs, annotations: {interface
com.fasterxml.jackson.annotation.JsonCreator=@com.fasterxml.jackson.annotation.JsonCreator(mode=DEFAULT)}]
     at [Source: (String)"{"name": "Joe", "age": 10}"; line: 1, column: 1]
        at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:62)
        at com.fasterxml.jackson.databind.DeserializationContext.reportBadTypeDefinition(DeserializationContext.java:1429)
        at com.fasterxml.jackson.databind.deser.BasicDeserializerFactory._addExplicitPropertyCreator(BasicDeserializerFactory.java:684)
        at com.fasterxml.jackson.databind.deser.BasicDeserializerFactory._addExplicitAnyCreator(BasicDeserializerFactory.java:715)
        at com.fasterxml.jackson.databind.deser.BasicDeserializerFactory._addDeserializerConstructors(BasicDeserializerFactory.java:465)
        at
com.fasterxml.jackson.databind.deser.BasicDeserializerFactory._constructDefaultValueInstantiator(BasicDeserializerFactory.java:337)
        at com.fasterxml.jackson.databind.deser.BasicDeserializerFactory.findValueInstantiator(BasicDeserializerFactory.java:255)
        at com.fasterxml.jackson.databind.deser.BeanDeserializerFactory.buildBeanDeserializer(BeanDeserializerFactory.java:214)
        at com.fasterxml.jackson.databind.deser.BeanDeserializerFactory.createBeanDeserializer(BeanDeserializerFactory.java:137)
        at com.fasterxml.jackson.databind.deser.DeserializerCache._createDeserializer2(DeserializerCache.java:411)
        at com.fasterxml.jackson.databind.deser.DeserializerCache._createDeserializer(DeserializerCache.java:349)
        at com.fasterxml.jackson.databind.deser.DeserializerCache._createAndCache2(DeserializerCache.java:264)
        at com.fasterxml.jackson.databind.deser.DeserializerCache._createAndCacheValueDeserializer(DeserializerCache.java:244)
        at com.fasterxml.jackson.databind.deser.DeserializerCache.findValueDeserializer(DeserializerCache.java:142)
        at com.fasterxml.jackson.databind.DeserializationContext.findRootValueDeserializer(DeserializationContext.java:477)
        at com.fasterxml.jackson.databind.ObjectMapper._findRootDeserializer(ObjectMapper.java:4178)
        at com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:3997)
        at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:2992)
        at ObjectMapperTest.lombok_all_args_fails(ObjectMapperTest.java:46)

ObjectMapperTest > pojo_all_args_fails FAILED
    com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Invalid type definition for type `pojo.PersonAllArgs`: Argument #0 has no
property name, is not Injectable: can not use as Creator [constructor for pojo.PersonAllArgs, annotations: {interface
com.fasterxml.jackson.annotation.JsonCreator=@com.fasterxml.jackson.annotation.JsonCreator(mode=DEFAULT)}]
     at [Source: (String)"{"name": "Joe", "age": 10}"; line: 1, column: 1]
        at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:62)
        at com.fasterxml.jackson.databind.DeserializationContext.reportBadTypeDefinition(DeserializationContext.java:1429)
        at com.fasterxml.jackson.databind.deser.BasicDeserializerFactory._addExplicitPropertyCreator(BasicDeserializerFactory.java:684)
        at com.fasterxml.jackson.databind.deser.BasicDeserializerFactory._addExplicitAnyCreator(BasicDeserializerFactory.java:715)
        at com.fasterxml.jackson.databind.deser.BasicDeserializerFactory._addDeserializerConstructors(BasicDeserializerFactory.java:465)
        at
com.fasterxml.jackson.databind.deser.BasicDeserializerFactory._constructDefaultValueInstantiator(BasicDeserializerFactory.java:337)
        at com.fasterxml.jackson.databind.deser.BasicDeserializerFactory.findValueInstantiator(BasicDeserializerFactory.java:255)
        at com.fasterxml.jackson.databind.deser.BeanDeserializerFactory.buildBeanDeserializer(BeanDeserializerFactory.java:214)
        at com.fasterxml.jackson.databind.deser.BeanDeserializerFactory.createBeanDeserializer(BeanDeserializerFactory.java:137)
        at com.fasterxml.jackson.databind.deser.DeserializerCache._createDeserializer2(DeserializerCache.java:411)
        at com.fasterxml.jackson.databind.deser.DeserializerCache._createDeserializer(DeserializerCache.java:349)
        at com.fasterxml.jackson.databind.deser.DeserializerCache._createAndCache2(DeserializerCache.java:264)
        at com.fasterxml.jackson.databind.deser.DeserializerCache._createAndCacheValueDeserializer(DeserializerCache.java:244)
        at com.fasterxml.jackson.databind.deser.DeserializerCache.findValueDeserializer(DeserializerCache.java:142)
        at com.fasterxml.jackson.databind.DeserializationContext.findRootValueDeserializer(DeserializationContext.java:477)
        at com.fasterxml.jackson.databind.ObjectMapper._findRootDeserializer(ObjectMapper.java:4178)
        at com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:3997)
        at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:2992)
        at ObjectMapperTest.pojo_all_args_fails(ObjectMapperTest.java:55)
```


### When `@JsonCreator` is absent:

```sh

ObjectMapperTest > lombok_all_args_fails FAILED
    com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `lombok.PersonAllArgs` (no Creators, like
default construct, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
     at [Source: (String)"{"name": "Joe", "age": 10}"; line: 1, column: 2]
        at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:67)
        at com.fasterxml.jackson.databind.DeserializationContext.reportBadDefinition(DeserializationContext.java:1451)
        at com.fasterxml.jackson.databind.DeserializationContext.handleMissingInstantiator(DeserializationContext.java:1027)
        at com.fasterxml.jackson.databind.deser.BeanDeserializerBase.deserializeFromObjectUsingNonDefault(BeanDeserializerBase.java:1290)
        at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserializeFromObject(BeanDeserializer.java:326)
        at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:159)
        at com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:4001)
        at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:2992)
        at ObjectMapperTest.lombok_all_args_fails(ObjectMapperTest.java:46)

ObjectMapperTest > pojo_all_args_fails FAILED
    com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `pojo.PersonAllArgs` (no Creators, like
default construct, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
     at [Source: (String)"{"name": "Joe", "age": 10}"; line: 1, column: 2]
        at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:67)
        at com.fasterxml.jackson.databind.DeserializationContext.reportBadDefinition(DeserializationContext.java:1451)
        at com.fasterxml.jackson.databind.DeserializationContext.handleMissingInstantiator(DeserializationContext.java:1027)
        at com.fasterxml.jackson.databind.deser.BeanDeserializerBase.deserializeFromObjectUsingNonDefault(BeanDeserializerBase.java:1290)
        at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserializeFromObject(BeanDeserializer.java:326)
        at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:159)
        at com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:4001)
        at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:2992)
        at ObjectMapperTest.pojo_all_args_fails(ObjectMapperTest.java:55)
```

## Reproducing

__Without Docker:__

```sh
./gradlew test --info
```

__With Docker:__

Using OpenJDK 9.0.1 image

```
./reproduce-with-docker.sh
```


## Information

The problem was produced under the following circumstances:

__Operating System:__

```sh
Linux Ubuntu 17.10
Kernel 4.13.0-32-generic
```

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

__Jackson versions tested:__

- 2.9.1
- 2.9.4
