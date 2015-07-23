register 'target/warcbase-0.1.0-SNAPSHOT-fatjar.jar';
DEFINE WarcLoader org.warcbase.pig.WarcLoader();

warc = LOAD '/raw/' USING WarcLoader AS (url: chararray, date: chararray, mime: chararray, content: bytearray, leafpaths);
a = FOREACH warc GENERATE url,date,leafpaths;
b = LIMIT a 2;
STORE b INTO '/finished/';
