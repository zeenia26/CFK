#!/bin/bash

kafka-acls --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 \
              --topic role \
              --command-config /mnt/consumer.properties \
              --add \
              --deny-principal User:'consumer' \
              --operation Write; 
kafka-acls --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 \
              --topic role \
              --command-config /mnt/consumer.properties \
              --add \
              --allow-principal User:'consumer' \
              --operation Read 
