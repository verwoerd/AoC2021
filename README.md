# Template for Advent Of Code
* Generate empty template per day in seperate packages
* Contains a general timing
* Includes a way for testing
* Includes a shared library automaticly included in every project
  

## First use
Set the correct project name in [`settings.gradle.kts`](./settings.gradle.kts)

Update the author in [`gradle.properties`](./gradle.properties) 

Update the year in [`gradle.properties`](./gradle.properties)

Add the following line the general [`~/.gradle/gradle.properties`](~/.gradle/gradle.properties)
```properties
aoc.session=123456789....ABCDEF
```
Replace the value with your value of your [aoc session token](https://github.com/GreenLightning/advent-of-code-downloader#how-do-i-get-my-session-cookie).

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

If configured correctly, your input file will be downloaded automaticly. If you don't want to use this feature, create a
file `input` in the `src/main/resources` folder with your input content.

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
./gradlew :dayXX:check
```


