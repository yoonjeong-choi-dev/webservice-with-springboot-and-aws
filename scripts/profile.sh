#!/bin/bash

# profile.sh : 배포를 진행할 애플리케이션 포트를 찾아

# 쉬고 있는 profile(프로세스) 찾기 : 해당 프로세스에 배포를 진행하고 nginx가 바라보도록 하기 위해
function find_idle_profile()
{
    # nginx가 현재 바라보는 프로세스가 정상 작동 중인지 확인
    # ProfileController에서 구현한 api를 통해 현재 바라보는 프로세스 확인
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

    # 현재 바라보는 프로세스가 정상 작동 중이 아니면 무조건 real2 프로세스에 배포 진행
    if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면 (즉, 40x/50x 에러 모두 포함)
    then
        CURRENT_PROFILE=real2
    else
        CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi

    # 현재 바라보는 프로세스의 반대쪽으로 배포 진행
    if [ ${CURRENT_PROFILE} == real1 ]
    then
      IDLE_PROFILE=real2
    else
      IDLE_PROFILE=real1
    fi

    # bash의 경우 값 반환 기능이 없음 => bash 파이프라인을 통해 반환값을 받아와 다음 작업 진행
    echo "${IDLE_PROFILE}"
}

# 쉬고 있는 프로세스의 port 찾기
function find_idle_port()
{
    # find_idle_profile를 통해 쉬고 있는 프로세스의 프로필 id 받아온다
    IDLE_PROFILE=$(find_idle_profile)

    if [ ${IDLE_PROFILE} == real1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}