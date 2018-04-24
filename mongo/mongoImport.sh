mongod --dbpath=/data/hsdb &
echo $! > mongo.pid
echo MongoDB started with PID $(cat mongo.pid)

echo Start to import Hearthstone card data...
mongoimport --db hearthstone --collection cards --drop --file /data/cards.collectible.json  --jsonArray

echo Shutting MongoDB down...
kill $(cat mongo.pid)
