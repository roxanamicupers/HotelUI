# HotelUI

Pre-requisites:
* maven is installed and is in the path
* java is installed and is in the path
* chromedriver, geckodriver and safaridriver are in the path


Steps to run the tests:
Run tests with default browser (Chrome):
* ./run_tests.sh

2. To run on a different browser:
* ./run_tests.sh $BROWSER_NAME
Example:
./run_tests.sh firefox 



To run the tests with Maven:
* start the Selenium grid by running the script:
./run_grid.sh
* run the tests
mvn test

Default browser configuration is set as chrome.
To change it:
* either update the env config in /src/main/resources/ui.conf
* run the tests as mvn test -Denv=firefox

Default timeoutTreshold is set as 10 seconds.
To change it:
* either update the timeout config in /src/main/resources/ui.conf and specify the new value in milliseconds
* run the tests as mvn test -Dtimeout=5000
