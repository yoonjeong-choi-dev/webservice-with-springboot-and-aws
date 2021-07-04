#!/bin/bash

# Git 을 pull받는 방식으로 어플리케이션 테스트 및 빌드 실행

# 빌드를 실행하는 디렉터리에 맞게 변수 변경해야 한다
REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=webservice-with-springboot-and-aws

cd $REPOSITORY/$PROJECT_NAME/
echo "> Git Pull"
git pull


echo "> 프로젝트 Build 시작"
chmod +x gradlew
./gradlew build


echo "> step1 디렉터리 이동"
cd $REPOSITYORY


echo "> Build 파일 복사"
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/


echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -fl $(ls -tr ${REPOSITORY}/*.jar | tail -n 1) | cut -d ' ' -f 1)

echo "현재 구동중인 어플리케이션 pid: $CURRENT_PID"
if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi


echo "> 새 어플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)
echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"
chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"
nohup java -jar \
    -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
    -Dspring.profiles.active=real \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
