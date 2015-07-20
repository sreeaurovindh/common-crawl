register 'target/warcbase-0.1.0-SNAPSHOT-fatjar.jar';

DEFINE WarcLoader org.warcbase.pig.WarcLoader();

warc = LOAD '/raw/' USING WarcLoader AS (url: chararray, date: chararray, mime: chararray, content: bytearray);
a = FOREACH warc GENERATE content;
b = LIMIT a 2;
STORE b INTO '/finished/';
