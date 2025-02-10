#!/usr/bin/env bash


apt update && apt install -y openssh-server curl
service ssh start