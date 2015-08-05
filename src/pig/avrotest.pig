REGISTER /usr/lib/pig/pig-0.14.0/lib/avro-1.7.5.jar
REGISTER /usr/lib/pig/pig-0.14.0/lib/json-simple-1.1.jar
REGISTER /usr/lib/pig/pig-0.14.0/lib/piggybank.jar
REGISTER /usr/lib/pig/pig-0.14.0/lib/jackson-core-asl-1.8.8.jar
REGISTER /usr/lib/pig/pig-0.14.0/lib/jackson-mapper-asl-1.8.8.jar

data  = load 'sampledata/' USING PigStorage('\t') AS (url:chararray , leafpathstr:chararray);

STORE data INTO './out' USING AvroStorage ();
