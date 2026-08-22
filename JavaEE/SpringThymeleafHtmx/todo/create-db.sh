#!/bin/bash -e

PROJECT_ROOT=$(dirname $(readlink -f ${0}))
BASENAME=$(basename ${0})

cd ${PROJECT_ROOT}/src/main/resources/database
sqlite3 todo.sqlite < todo.sql
echo "Database created"
ls -lL ${PROJECT_ROOT}/src/main/resources/database/todo.sqlite
