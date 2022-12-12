#!/bin/bash
java -jar /home/model_service.jar &
java -jar /home/car_service.jar &
java -jar /home/gateway.jar &
nginx -g 'daemon off;'