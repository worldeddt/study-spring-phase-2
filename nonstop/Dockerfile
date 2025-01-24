FROM ubuntu:20.04

# 기존 패키지 업데이트
RUN apt update
RUN apt install sudo
RUN sudo apt upgrade -y

# Docker 설치를 위한 필수 패키지 설치
RUN sudo apt install -y apt-transport-https ca-certificates curl software-properties-common

# Docker 공식 GPG 키 추가
RUN curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

# Docker 저장소 추가
RUN echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Docker 설치
RUN sudo apt update
RUN sudo apt install -y docker-ce docker-ce-cli containerd.io

# Docker 서비스 시작 및 활성화
RUN sudo systemctl start docker
RUN sudo systemctl enable docker

# 설치 확인
RUN docker --version

# 최신 버전 Docker Compose 다운로드
RUN sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

# 실행 권한 부여
RUN sudo chmod +x /usr/local/bin/docker-compose

# 설치 확인
RUN docker-compose --version



