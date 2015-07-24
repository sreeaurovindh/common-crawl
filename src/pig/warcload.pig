register 'target/warcbase-0.1.0-SNAPSHOT-fatjar.jar';
DEFINE WarcLoader org.warcbase.pig.WarcLoader();

warc = LOAD '/raw/' USING WarcLoader AS (url: chararray, date: chararray, mime: chararray, leafpaths);
a = FOREACH warc GENERATE mime,leafpaths;
b = LIMIT a 850;
STORE b INTO '/finished/';
