#!/bin/sh
PID=`ps auxw | grep -i glass-cloud | grep -v grep | awk ' { print $2 } '`
echo "kill process $PID"
ps axuw | grep $PID | grep -v grep
kill $PID
