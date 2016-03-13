#!/bin/bash

for f in *.sql
do
	`mysql -uroot < $f`
	echo "create table - $f"
done
