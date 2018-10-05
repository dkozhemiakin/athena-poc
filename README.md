# athena-poc

```sh
|-----------------------------------------
| Ms    |   Bytes     |          Task name
|-----------------------------------------
|001473  000000137789  SELECT * FROM csv_athena_table LIMIT 100
|000998  000018475908  SELECT * FROM parq_athena_table LIMIT 100
|011849  000059244544  SELECT * FROM csv_athena_table WHERE region = 'Sub-Saharan Africa'
|003903  000065934455  SELECT * FROM parq_athena_table WHERE region = 'Sub-Saharan Africa'
|009397  000059244544  SELECT * FROM csv_athena_table WHERE region = 'Sub-Saharan Africa' AND item_type IN ('Fruits', 'Clothes')
|001554  000066678944  SELECT * FROM parq_athena_table WHERE region = 'Sub-Saharan Africa' AND item_type IN ('Fruits', 'Clothes')
|009734  000059244544  SELECT order_id, order_date FROM csv_athena_table
|004321  000015917380  SELECT order_id, order_date FROM parq_athena_table
|015380  000059244544  SELECT * FROM csv_athena_table
|011636  000065357739  SELECT * FROM parq_athena_table
```