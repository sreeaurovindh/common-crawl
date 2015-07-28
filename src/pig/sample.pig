register 'target/warcbase-0.1.0-SNAPSHOT-fatjar.jar';
register 'splitElements.py' using jython as leafCombiner;
define SetUnion datafu.pig.sets.SetUnion();
define SetIntersect datafu.pig.sets.SetIntersect();

data  = load 'sampledata/' USING PigStorage('\t') AS (url:chararray , leafpathstr:chararray);

byUrlXpaths = GROUP data  by (url,leafpathstr);
UrlXpathsCount = FOREACH byUrlXpaths GENERATE
        FLATTEN(group) AS (url,leafpathstr),
        COUNT(data) AS urlpath_count;


/* orderbyXpaths = ORDER byUrlXpathsCount by urlpath_count desc,url;*/

byUrl = GROUP UrlXpathsCount by url;





/* min and max for each group */

/*maxminByUrl: {urls_max::xpathstr: chararray,urls_max::url: chararray,urls_max::urlpath_count: long,urls_min::xpathstr: chararray,urls_min::url: chararray,urls_min::urlpath_count: long}
*/
midRangeByUrl = FOREACH byUrl{
	urls_desc = order UrlXpathsCount by urlpath_count desc;
	urls_max = limit urls_desc 1;
	urls_asc = order UrlXpathsCount by urlpath_count asc;
	urls_min = limit urls_asc 1;
	
        GENERATE FLATTEN(urls_max),FLATTEN(urls_min);
};

/* Since the values are for each group we get the mimmax value */
computeMidRange = FOREACH midRangeByUrl generate urls_max::url as mid_url,((DOUBLE)urls_max::urlpath_count+(DOUBLE) urls_min::urlpath_count)/2 as midRange;


/* Join computeMidRange  and UrlXpathsCount */
midRangeJoin = join UrlXpathsCount by url , computeMidRange by mid_url using 'replicated';
midRangeOut = FOREACH midRangeJoin GENERATE UrlXpathsCount::url as url,UrlXpathsCount::leafpathstr as leafpathstr,
	UrlXpathsCount::urlpath_count as urlpath_count,computeMidRange::midRange as midRange;


templates = FILTER midRangeOut by urlpath_count > midRange;
variations = FILTER midRangeOut by urlpath_count <= midRange;


byUrlVariations = GROUP variations by url;
variationAvg = FOREACH byUrlVariations GENERATE FLATTEN(variations.(url,leafpathstr,urlpath_count)),AVG(variations.urlpath_count) as avgPathCnt;
/*Should we have >= in filter below?? test with data*/

filteredVariations = FILTER variationAvg by urlpath_count >= avgPathCnt;

templatesOut = FOREACH templates GENERATE url,leafpathstr,urlpath_count,leafCombiner.splitBag(leafpathstr);
variationsOut = FOREACH filteredVariations GENERATE url,leafpathstr,urlpath_count,leafCombiner.splitBag(leafpathstr);

/* Remove Minor abberations of templates
# Check what happens with sample data?? */
/** THINK about a filter on variationsOut here!!*/




join_url = JOIN  templatesOut  BY  url,variationsOut BY url;

jaccard_sim = FOREACH join_url {
sorted_b1 = ORDER templatesOut::leafpaths by xpaths;
sorted_b2 = ORDER variationsOut::leafpaths by xpaths;
setintersect = SetIntersect(sorted_b1,sorted_b2);
setunion = SetUnion(sorted_b1,sorted_b2);
GENERATE templatesOut::url AS url,templatesOut::leafpathstr as template_leafpathstr,templatesOut::urlpath_count as templates_count,variationsOut::null::leafpathstr as variations_leafpathstr,variationsOut::null::urlpath_count as variations_count,(DOUBLE)COUNT(setintersect) /(DOUBLE) COUNT(setunion) AS jaccard;

};


/*Jaccard should be 0.8 change it later*/
templates_match  = FILTER jaccard_sim by jaccard > 0.49;

/* we find the maximum jaccard for every group of variatiosn and get the maximum similarity from it.*/
similaritybyVariations = GROUP templates_match by (url,variations_leafpathstr);
maxSimilarity = FOREACH similaritybyVariations{
	sim_desc = order templates_match by jaccard desc;
	sim_max = limit sim_desc 1;
	GENERATE FLATTEN(sim_max);

};

similarityAll = FOREACH maxSimilarity GENERATE sim_max::url as url,sim_max::template_leafpathstr as leafpathstr,sim_max::templates_count as template_count,sim_max::variations_count as var_count;

/* For each group of (url,leafpathstr,template_count) we have to sum up the var_count */

gather_templates = GROUP similarityAll by (url,leafpathstr,template_count);
template_sums = FOREACH gather_templates GENERATE FLATTEN(group) AS (url,leafpathstr,template_count),SUM(similarityAll.var_count) as var_sum;


/*Inner Join both templates to arrive at final number */

template_select  = FOREACH templatesOut GENERATE url,leafpathstr,urlpath_count;
template_join = join template_select by (url,leafpathstr,urlpath_count) LEFT OUTER, template_sums by (url,leafpathstr,template_count);

templates_final = FOREACH template_join GENERATE  template_select::url AS url,template_select::leafpathstr AS leafpathstr,(template_sums::var_sum IS NULL ? template_select::urlpath_count :template_select::urlpath_count+ template_sums::var_sum)  as occurence;















DUMP byUrlXpathsCount
   


