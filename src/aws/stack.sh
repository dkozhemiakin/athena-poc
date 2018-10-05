#!/usr/bin/env bash
aws cloudformation update-stack --stack-name test-athena-poc --template-body file://./cloudformation.yaml --profile eis-deliverydevqa
