FROM nginx

RUN apt-get update && \
    apt-get upgrade && \
    apt-get install -y curl

COPY nginx-proxy.conf /etc/nginx/conf.d/default.conf