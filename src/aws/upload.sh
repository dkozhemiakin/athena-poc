#!/usr/bin/env bash
aws s3 cp ./dataset.csv.gz s3://test-ebsco-athena-poc/data/ --profile eis-deliverydevqa
