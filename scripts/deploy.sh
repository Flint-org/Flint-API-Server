#!/bin/bash

REPOSITORY=/home/ubuntu/git-action

echo "> 현재 구동중인 애플리케이션 PID 확인"

CURRENT_PID=$(pgrep -f chord-player)

echo "$CURRENT_PID"

if [ -z $CURRENT_PID ]; then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"
echo "> Build 파일 복사"

cp $REPOSITORY/build/libs/*.jar $REPOSITORY/

JAR_NAME=flint-0.0.1-SNAPSHOT.jar

echo "> JAR Name: $JAR_NAME"

nohup java -jar $REPOSITORY/$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &