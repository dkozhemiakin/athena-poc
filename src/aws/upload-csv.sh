#!/usr/bin/env bash
aws s3 cp ./dataset.csv.gz s3://test-ebsco-athena-poc/csv/dataset.csv.gz --profile eis-deliverydevqa
