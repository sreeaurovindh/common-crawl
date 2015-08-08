register 'target/warcbase-0.1.0-SNAPSHOT-fatjar.jar';
DEFINE WarcLoader org.warcbase.pig.WarcLoader();

warc = LOAD '/warcdata' USING WarcLoader AS (url: chararray, ipaddress: chararray, leafpathstr: chararray);

a = FOREACH warc GENERATE url,ipaddress;
b = LIMIT a 100;
STORE b INTO '/warcentity';
