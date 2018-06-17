# TODO 考虑改用 alpine 镜像

FROM ubuntu:16.04
MAINTAINER Windy <chenchuowen@foxmail>

RUN apt-get -yqq update
RUN apt-get -yqq install nodejs npm
RUN ln -s /usr/bin/nodejs /usr/bin/node
RUN mkdir -p /opt/nodeapp

ADD . /opt/nodeapp

WORKDIR /opt/nodeapp
RUN npm install
RUN npm install -g nodemon
EXPOSE 3000

CMD ["npm", "run", "build"]
