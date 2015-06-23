
import glob
import json
import re

dir_loc = '/home/sree/Projects/common-crawl/data/indeed-2014-52/*'
output_loc  = '/home/sree/Projects/common-crawl/data/exploreds/'
tld_dict = {}
def urltypes(dir_loc):
    # Iterate over files in directory
    for name in glob.glob(dir_loc):
        file_jsons = open(name,mode ='r')
        #Load the JSON file
        for line in file_jsons:
            urls_pg  = json.loads(line)  
            first_level = urls_pg['urlkey'].split('/')[1]
            first_level = site_indeed(first_level)
            if first_level not in tld_dict:
                print(first_level)
                print(line)
                tld_dict[first_level] = 'true'
                #output_file = tld_dict[first_level]
                #output_file.write(str(urls_pg))
            #else:
                #tld_dict[first_level] = open(output_loc + '/' + first_level + '.txt',mode ='a')
        
    
    
def site_indeed(first_level):
    #Example 1 : q-mortgage-auditor-l-mooresville,-nc-jobs.html
    if re.search('-jobs.html$',first_level,re.IGNORECASE) is not None:
        first_level = 'NONURL-jobspage-formatted'
    #Jobs with Location
    # Example 2: jobs?l=west+palm+beach,+fl&q=human+resources&start=40    
    if re.search('^jobs\?l=',first_level,re.IGNORECASE) is not None:
        first_level = 'NONURL-jobspage-urlquery-#Location#Query#'
    #Example 3 : jobs?q=branch+customer+service+representative&start=30
    #Example 4 : jobs?nc=frm&q=ziegler+inc
    if re.search('^jobs.*\w*\?q=',first_level,re.IGNORECASE) is not None and re.search('^jobs.*\?l=',first_level,re.IGNORECASE) is  None:
        first_level = 'NONURL-jobspage-urlquery#Query#'    
    return first_level
    
urltypes(dir_loc)