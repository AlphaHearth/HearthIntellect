FROM mongo
MAINTAINER Windy <chenchuowen@foxmail>

RUN mkdir -p /data/hsdb

ADD ./cards.collectible.json /data
ADD ./mongoImport.sh /bin
RUN chmod +x /bin/mongoImport.sh \
    && /bin/mongoImport.sh \
    && rm /data/cards.collectible.json \
    && rm /bin/mongoImport.sh

RUN chown -R mongodb:mongodb /data/hsdb

EXPOSE 27017

CMD ["mongod", "--dbpath", "/data/hsdb"]
