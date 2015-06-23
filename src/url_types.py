
import glob
import json
import re

dir_loc = '/home/sree/Projects/common-crawl/data/indeed-2014-52/*'
explore_dataset = '/home/sree/Projects/common-crawl/data/exploreds/'

def urltypes(dir_loc):
    # Iterate over files in directory
    for name in glob.glob(dir_loc):
        file_jsons = open(name,mode ='r')
        #Load the JSON file
        for line in file_jsons:
            urls_pg  = json.loads(line)  
            print(urls_pg['url'])
        
    
    

urltypes(dir_loc)