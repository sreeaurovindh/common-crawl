register 'target/warcbase-0.1.0-SNAPSHOT-fatjar.jar';
DEFINE WarcLoader org.warcbase.pig.WarcLoader();

warc = LOAD '/raw/' USING WarcLoader AS (url: chararray, date: chararray, mime: chararray, leafpathstr: chararray);
a = FOREACH warc GENERATE mime,leafpathstr;
b = LIMIT a 3;
STORE b INTO '/finished/';
