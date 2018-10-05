from pyspark import SparkContext
from pyspark.sql import SQLContext
from pyspark.sql.types import *


if __name__ == "__main__":
    sc = SparkContext(appName="CSV2Parquet")
    sqlContext = SQLContext(sc)

    schema = StructType([
            StructField("region", StringType(), True),
            StructField("country", StringType(), True),
            StructField("item_type", StringType(), True),
            StructField("sales_channel", StringType(), True),
            StructField("order_priority", StringType(), True),
            StructField("order_date", StringType(), True),
            StructField("order_id", StringType(), True),
            StructField("ship_date", StringType(), True),
            StructField("units_sold", StringType(), True),
            StructField("unit_price", StringType(), True),
            StructField("unit_cost", StringType(), True),
            StructField("total_revenue", StringType(), True),
            StructField("total_cost", StringType(), True),
            StructField("total_profit", StringType(), True)
    ])

    rdd = sc.textFile("./dataset.csv").map(lambda line: line.split(","))
    df = sqlContext.createDataFrame(rdd, schema)
    df.write.parquet('./input-parquet')
