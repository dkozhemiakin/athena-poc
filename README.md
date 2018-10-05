# athena-poc

```sh
|-----------------------------------------
| Ms    |   Bytes     |          Task name
|-----------------------------------------
|001428  000000137789  SELECT * FROM csv_athena_table LIMIT 100
|000186  000000000000  SELECT * FROM para_athena_table LIMIT 100
|012389  000059244544  SELECT * FROM csv_athena_table WHERE region = 'Sub-Saharan Africa'
|003887  000065934455  SELECT * FROM parq_athena_table WHERE region = 'Sub-Saharan Africa'
|010049  000059244544  SELECT * FROM csv_athena_table WHERE region = 'Sub-Saharan Africa' AND item_type IN ('Fruits', 'Clothes')
|003048  000066678944  SELECT * FROM parq_athena_table WHERE region = 'Sub-Saharan Africa' AND item_type IN ('Fruits', 'Clothes')
|010262  000059244544  SELECT order_id, order_date FROM csv_athena_table
|003198  000015917380  SELECT order_id, order_date FROM parq_athena_table
|017243  000059244544  SELECT * FROM csv_athena_table
|011255  000065357739  SELECT * FROM parq_athena_table
```