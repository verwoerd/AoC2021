# Template for Advent Of Code
* Generate empty template per day in seperate packages
* Contains a general timing
* Includes a way for testing
* Includes a shared library automaticly included in every project
  

## First use
Set the correct project name in [`settings.gradle.kts`](./settings.gradle.kts)

Update the author in [`gradle.properties`](./gradle.properties) 

## Generate new day
* Generate a new day module
```shell script
./gradlew generateNextDay
```

## Implement our problem
* paste the input in `src/main/resources/input`
* Implement the code in `solution.kt` and return the final answer
* The framework will take care of printing
* run the application by
```shell script
./gradlew run
```
Or for a specific day for day XX(zerofill)
```shell script
./gradlew :dayXX:run
```

## Testing your Code

* Paste the input in `src/test/resources/part1/example1.input`
* Paste the ouptut in `src/test/resources/part1/example1.output` 
* To include examples update the examples count in `Part1Test.kt`
* Execute the tests by running 
```shell script
./gradlew check
```
Or for a specific day for day XX(zerofill)
```shell script
./gradlew :dayXX:run
```


