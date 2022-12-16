FROM nginx

COPY /WebView /usr/share/nginx/html/
COPY nginx-server.conf /etc/nginx/conf.d/default.conf

COPY Model-0.0.1-SNAPSHOT.jar /home/model_service.jar
COPY Car-0.0.1-SNAPSHOT.jar /home/car_service.jar
COPY CarApiGateway-0.0.1-SNAPSHOT.jar /home/gateway.jar
COPY start.sh /home/start.sh

RUN apt-get update && \
    apt-get upgrade && \
    apt-get install -y openjdk-17-jre-headless curl && \
    apt-get clean;

CMD ["bash", "/home/start.sh"]