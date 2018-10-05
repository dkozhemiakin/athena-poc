#!/usr/bin/env bash
aws s3 sync ./parquet/ s3://test-ebsco-athena-poc/parq/ --profile eis-deliverydevqa
