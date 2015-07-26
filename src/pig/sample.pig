register 'target/warcbase-0.1.0-SNAPSHOT-fatjar.jar';
define SetUnion datafu.pig.sets.SetUnion();
define SetIntersect datafu.pig.sets.SetIntersect();

data  = load 'sampledata/' USING PigStorage('\t') AS (url:chararray , leafpaths:Bag{t:tuple(xpath:chararray)});
datacopy  = load 'sampledata/' USING PigStorage('\t') AS (url:chararray , leafpaths:Bag{t:tuple(xpath:chararray)});
join_url = JOIN  data  BY  url,datacopy BY url;

jaccard_sim = FOREACH join_url {
sorted_b1 = ORDER data::leafpaths by xpath;
sorted_b2 = ORDER datacopy::leafpaths by xpath;
setintersect = SetIntersect(sorted_b1,sorted_b2);
setunion = SetUnion(sorted_b1,sorted_b2);
GENERATE data::url,(DOUBLE)COUNT(setintersect) /(DOUBLE) COUNT(setunion) AS jaccard;

};
templates_match  = FILTER jaccard_sim by jaccard > 0.75;
DUMP templates_match;


