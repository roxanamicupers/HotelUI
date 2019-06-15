#!/bin/sh
if [ -z "$1" ]
then
    ./run_grid.sh & sleep 10 ; mvn clean test
else 
   ./run_grid.sh &sleep 10; mvn clean test -Denv=$1
fi
lsof -t -i :4444 | xargs kill
