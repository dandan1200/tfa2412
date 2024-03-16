# Exchange Program
CC_19_Fri_12_Hunter_Group_3 - SOFT2412    
    
Dependencies:    
[gradle](https://gradle.org/)  
[jacoco](https://www.jacoco.org/jacoco/)  
  
The classes used to implement the exchange can be found in ```app/src/main/java/assignment1/app```.  
  
Accompanying JSON exchange-data-storage files can be found in ```app/src/main/resources```.  
  
Test-cases for the various classes can be found in ```app/src/test/java```.  
  
Jenkins has been integrated on a local-level, as opposed to being hosted.  
  
# Build  
  
To build, run  
```gradle build```  
  
# Firing up the exchange  
  
To start the exchange, run  
```gradle run```  
  
The user will then face a series of command-line prompts, which can be answered using the command-line.  
  
# Testing  
  
The exchange utilises the JaCoCo plugin to determine code-coverage.  
  
To start, run  
```gradle test```   
  
This will output an index.html file containing the coverage report in ```app/build/jacocoHtml/index.html```  
To view the report, open the file using any HTML viewer.  
  