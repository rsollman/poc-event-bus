Download kafka and install and start as follows:

$ tar -xzf kafka_2.13-2.7.0.tgz
$ cd kafka_2.13-2.7.0
$ ./bin/zookeeper-server-start.sh config/zookeeper.properties
$ ./bin/kafka-server-start.sh config/server.properties

Start spring-boot app

curl -X POST -F 'message=test' http://localhost:9000/kafka/publish

curl -X POST http://localhost:9090/history/create-offer -F createOffer='{"id":"myid", "createTime":"1970-01-01T00:00:00Z", "name":"myName"}'

curl -X POST --data ~/work/code-it/github/poc-event-bus/event-bus/tmp.json  http://localhost:9090/history/create-offer --header 'Content-Type: application/json'

