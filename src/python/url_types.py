
import glob
import json
import re

dir_loc = '/home/sree/Projects/common-crawl/data/simplyhired-CC-MAIN-2015-06/*'
output_loc  = '/home/sree/Projects/common-crawl/data/exploreds/'
tld_dict = dict()
dom_dict = dict()

def urltypes(dir_loc):
    '''
    This method is used to classify the URLs based on different format
    of web pags. So that we can crawl them using Hadoop / MapReduce
    '''
    cmpCnt = 0
    elseCnt = 0
    listingsCnt = 0  
    fileCnt = 0
    jobtrendCnt = 0
    # Iterate over files in directory
    for name in glob.glob(dir_loc):
        file_jsons = open(name,mode ='r')
        #Load the JSON file
        for line in file_jsons:
            urls_pg  = json.loads(line)  
            url_segments = urls_pg['urlkey'].split('/')
            domain_level = url_segments[0].split('"')[0]
            if not is_domain_allowed(domain_level):
                continue
            first_level = url_segments[1]
            first_level,cmpCnt,elseCnt,listingsCnt,jobtrendCnt = site_indeed(first_level,cmpCnt,elseCnt,listingsCnt,jobtrendCnt)
            #print(cmpCnt,elseCnt,listingsCnt)
            if first_level not in tld_dict:
                print(first_level)
                print(line)
               
                tld_dict[first_level] =1
                #output_file = tld_dict[first_level]
                #output_file.write(str(urls_pg))
            else:
                tld_dict[first_level] +=1
                #tld_dict[first_level] = open(output_loc + '/' + first_level + '.txt',mode ='a')
    print('cmpCnt',',',str(cmpCnt))
    print('listingCnt',',',str(listingsCnt))
    print('elseCnt',',',str(elseCnt))
    print('FileCnt',str(fileCnt))
    print('JobCnt',str(jobtrendCnt))
    print(dom_dict)
    print(tld_dict)
        

def is_domain_allowed(domain_str):
    '''
    This method chooses different domains that we are 
    interested in.
    (Job Postings in English)
    '''
    #The strings end with ) and hence these are not excluded.
    denied_domains = ['blog)','ca)','kr)','de)','dk)','co)','gr)','hu)','ar)','aq)','au)','at)','be)','bh)','vn)','jp)','it)','pl)','om)','press)','ru)','ve)','tr)','tw)','se)','sa)','cn)','ro)','resumefaq)','qa)','nz)','id)','ie)','kw)','es)','th)','il)','emplois)','cz)','offers)','no)']
    domains_list = domain_str.split(",")
    domain_name = domains_list[len(domains_list)-1]
    if domain_name in dom_dict.keys():
        dom_dict[domain_name] += 1
    else:
        dom_dict[domain_name] = 1
        
    if domains_list[len(domains_list)-1] in denied_domains:
        return False
    return True
    
def site_indeed(first_level,cmpCnt,elseCnt,listingsCnt,jobtrendCnt):
    '''
    This method provides regular expressions for different URLs 
    present in indeed.com TLD.
    '''
    #Example 1 : q-mortgage-auditor-l-mooresville,-nc-jobs.html
    if re.search('-jobs.html$',first_level,re.IGNORECASE) is not None:
        first_level = 'NONURL-jobspage-formatted'
        listingsCnt += 1
        return first_level,cmpCnt,elseCnt,listingsCnt,jobtrendCnt
    
    
    
    #Jobs with Location
    # Example 2: jobs?l=west+palm+beach,+fl&q=human+resources&start=40    
    if re.search('^jobs',first_level,re.IGNORECASE) is not None:
        first_level = 'Job Listing'
        listingsCnt += 1
        return first_level,cmpCnt,elseCnt,listingsCnt,jobtrendCnt
    
    if re.search('jobs$',first_level,re.IGNORECASE) is not None:
        first_level = 'Job Listing'
        listingsCnt += 1
        return first_level,cmpCnt,elseCnt,listingsCnt,jobtrendCnt    
    
    first_level = first_level.split('?')[0]
    
    ##Example 3 : jobs?q=branch+customer+service+representative&start=30
    ##Example 4 : jobs?nc=frm&q=ziegler+inc
    #if re.search('^jobs(.\?)**(q=)+',first_level,re.I) is not None and re.search('^jobs(\?)+.*(\&l=)+',first_level,re.I) is  None:
        #first_level = 'NONURL-jobspage-urlquery#Query#NO-Loc#'    
        #listingsCnt +=1
        #return first_level,cmpCnt,elseCnt,listingsCnt,jobtrendCnt

    #if re.search('^jobtrends',first_level,re.I) is not None:
        #first_level = 'jobtrends'
        #jobtrendCnt += 1
        #return first_level,cmpCnt,elseCnt,listingsCnt,jobtrendCnt
    
    elseCnt += 1
    return first_level,cmpCnt,elseCnt,listingsCnt,jobtrendCnt
urltypes(dir_loc)