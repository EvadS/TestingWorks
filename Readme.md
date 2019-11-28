# SALESHERO INTERVIEW CHALLENGE
Given with a line separated text file of integers ranging anywhere from Integer.MIN to
Integer.MAX of size 1024MB,  the program should be able to produce line separated text
file which has the sorted content of the input file.

## Following preconditions
* The program should be able to run with a memory constraint of 100MB i.e. the
-Xmx100m.
*  The file can have duplicate integers.
*  The text in the file has only integers which are line separated and no other
characters.

### How to run  

* change permission in maven file 

```chmod +x mvnw ```

*  need to setup maven wrapper:

``` mvn -N io.takari:maven:wrapper```

* build jar file 
``` mvn clean package  ```

* run compiled file 

``` java -jar target/app-jar-with-dependencies.jar ```