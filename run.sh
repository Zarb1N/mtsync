#!/bin/bash
docker build -t thecntgfy/mtsync ./back/demo && docker build -t zarb1n/mtsync ./front
docker compose stop && docker compose up -d
