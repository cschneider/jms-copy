# JMS-COPY

Allows to copy all messages from all queues from one ActiveMQ provider to another.


## Build

mvn clean install

## Usage

java -jar target/jms-copy-1.0-SNAPSHOT.jar -surl tcp://localhost:61616 -durl tcp://localhost:51616

Usage: <main class> [options]
  Options:
  * -durl
       URL of destination ActiveMQ
    -filter
       Filter for queues to copy like *.DLQ
       Default: *
    -password
       Password
  * -surl
       URL of source ActiveMQ. Example: tcp://localhost:61616
    -user
       Username
