register 'target/warcbase-0.1.0-SNAPSHOT-fatjar.jar';
register 'combineElements.py' using jython as leafCombiner;
define SetUnion datafu.pig.sets.SetUnion();
define SetIntersect datafu.pig.sets.SetIntersect();

data  = load 'sampledata/' USING PigStorage('\t') AS (url:chararray , leafpaths:Bag{t:tuple(xpath:chararray)});

pathFreq = FOREACH data GENERATE url,leafCombiner.concat_bag(leafpaths.xpath) as xpathstr;
byUrlXpaths = GROUP pathFreq by (xpathstr,url);
byUrlXpathsCount = FOREACH byUrlXpaths GENERATE
        FLATTEN(group) AS (xpathstr,url),
        COUNT(pathFreq) AS urlpath_count;


orderbyXpaths = ORDER byUrlXpathsCount by urlpath_count desc,url;

byUrl = GROUP orderbyXpaths by url;
/* min and max for each group */
maxminByUrl = FOREACH byUrl{
	urls_desc = order orderbyXpaths by urlpath_count desc;
	urls_max = limit urls_desc 1;
	urls_asc = order orderbyXpaths by urlpath_count asc;
	urls_min = limit urls_asc 1;
        GENERATE urls_max as max_count,urls_min as min_count,FLATTEN(group) as url;
};





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

DUMP byUrlXpathsCount
   


