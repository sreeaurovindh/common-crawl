DATA  = load 'sampledata/' USING PigStorage('\t') AS (url:chararray , leafpaths:Bag{t:tuple(xpath:chararray)});
DATACOPY  = load 'sampledata/' USING PigStorage('\t') AS (url:chararray , leafpaths:Bag{t:tuple(xpath:chararray)});
JOIN_URL = JOIN  DATA  BY  url,DATACOPY BY url;
DUMP JOIN_URL;


