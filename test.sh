#!/usr/bin/env bash

curl -v \
    -H 'roleId: testRole' \
    -H 'clientSystemId: testSourceSystemName' \
    -H 'userId: testuserId' \
    -H 'x-correlation-id: 123456790' \
    -X 'GET' http://localhost:7777/count