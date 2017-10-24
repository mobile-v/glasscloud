#!/bin/sh
PID=`ps auxw | grep -i storyWall | grep -v grep | awk ' { print $2 } '`
echo "kill process $PID"
ps axuw | grep $PID | grep -v grep
kill $PID