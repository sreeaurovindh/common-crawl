register 'target/warcbase-0.1.0-SNAPSHOT-fatjar.jar';
register 'combineElements.py' using jython as leafCombiner;
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
GENERATE data::url,setunion as bagunion,(DOUBLE)COUNT(setintersect) /(DOUBLE) COUNT(setunion) AS jaccard;

};

/*templates_match  = FILTER jaccard_sim by jaccard > 0.75;*/
urlpath_str = FOREACH jaccard_sim GENERATE data::url as finurl,leafCombiner.concat_bag(bagunion.xpath) as xpaths,jaccard;
byUrlXpaths = GROUP urlpath_str by (xpaths,finurl);
byUrlXpathsCount = FOREACH byUrlXpaths GENERATE
	FLATTEN(group) AS (xpaths,finurl),
	COUNT(urlpath_str) AS urlpath_count;

DUMP byUrlXpathsCount
   


