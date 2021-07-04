#!/bin/bash


# 배포를 진행할 애플리케이션 포트를 찾기 위해 profile.sh를 import
ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

# 배포를 진행할 애플리케이션 포트 설정
IDLE_PORT=$(find_idle_port)

# 배포를 진행할 프로세스를 포트 번호로 확인 후, 해당 포트가 열려 있는 경우 그 프로세스를 종료
echo "> $IDLE_PORT 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi