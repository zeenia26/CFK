#!/bin/bash

kafka-acls --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 \
              --topic role \
              --command-config /mnt/producer.properties \
              --add \
              --allow-principal User:'producer' \
              --operation Write; 
kafka-acls --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 \
              --topic role \
              --command-config /mnt/producer.properties \
              --add \
              --deny-principal User:'producer' \
              --operation Read 
