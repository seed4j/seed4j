#!/usr/bin/env bash

echo "*** Stopping Seed4J in 5sec..."
sleep 5
kill $(cat .pid-seed4j)
