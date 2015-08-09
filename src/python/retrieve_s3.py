# Code modified from  https://github.com/trivio/common_crawl_index
import boto
from gzip import GzipFile
import io
from boto.s3.key import Key
import os
 
import glob
import json
import re
#{"urlkey": "com,indeed)/jobs?l=ohio&q=desktop+support+specialist&start=30", "timestamp": "20141226155330", "status": "200",
#"url": "http://www.indeed.com/jobs?q=Desktop+Support+Specialist&l=Ohio&start=30", 
#"filename": "common-crawl/crawl-data/CC-MAIN-2014-52/segments/1419447549371.17/warc/CC-MAIN-20141224185909-00078-ip-10-231-17-201.ec2.internal.warc.gz", "length": "21377", "mime": "text/html", "offset": "262285888", "digest": "FA5CZXSX6ELIPPBBHENFKYTOGSOR6GBS"}

#{"urlkey": "com,indeed)/q-controller-l-montana-jobs.html", "timestamp": "20141229004935", "status": "200", "url": "http://www.indeed.com/q-Controller-l-Montana-jobs.html", "filename": "common-crawl/crawl-data/CC-MAIN-2014-52/segments/1419447559962.154/warc/CC-MAIN-20141224185919-00083-ip-10-231-17-201.ec2.internal.warc.gz", "length": "25146", "mime": "text/html", "offset": "522986886", "digest": "44JSNQ6IVDDF5OI6X5Q7FY7W7HBRMONB"}

#--------------------------------------------------------------------------------------------------------------------
#length = 23992
#offset = 253568159
#keyname = 'common-crawl/crawl-data/CC-MAIN-2014-52/segments/1419447562878.33/warc/CC-MAIN-20141224185922-00065-ip-10-231-17-201.ec2.internal.warc.gz'
#out_dir = '/home/sree/Projects/common-crawl/data/indeed-html/'
#out_file = 'output.txt'
#---------------------------------------------------------------------------------------------------

dir_loc = '/home/sree/Projects/common-crawl/data/indeed-2015-27-may/*'
index_foldername ='/home/sree/Projects/common-crawl/data/index_may2015/'
html_output_dir = '/home/sree/Projects/common-crawl/data/indeed-html/'



html_extn = '.html'
def get_aws_bucket():
    conn = boto.connect_s3(anon=True)
    public_dataset = conn.get_bucket('aws-publicdatasets')     
    return public_dataset

def fetch_and_write_html(public_dataset):
    folder_counter = 6
    html_counter = 6
    for name in glob.glob(dir_loc):
        file_jsons = open(name,mode ='r')
        print('Processing...'+str(name))
        folder_counter += 1
        index_folder = index_foldername  + str(folder_counter) +'/'
        html_folder = html_output_dir +str(folder_counter) +'/'
        if not os.path.exists(index_folder): 
            os.makedirs(index_folder)
        if not os.path.exists(html_folder): 
            os.makedirs(html_folder)        
        #Load the JSON file
        for line in file_jsons:
            urls_pg  = json.loads(line)  
            keyname = urls_pg['filename']
            offset = int(urls_pg['offset'])
            length = int(urls_pg['length'])
            url = urls_pg['url']
            html_counter +=1
            
            file_name_html = str(folder_counter)+'/'+str(html_counter)+".html"
            update_html_index_file(file_name_html,url,folder_counter)
            #print(url,file_name_html)
            ##url_segments = urls_pg['urlkey'].split('/')
            ##domain_level = url_segments[0].split('"')[0]    
            get_warc_from_s3(public_dataset,keyname,offset,length,html_output_dir,file_name_html)
    
    
    
      
    

def get_warc_from_s3(bucket,keyname,offset,length,file_dir,file_dest):
    file_dest = file_dir + file_dest
    key = bucket.lookup(keyname)
    end = offset + length - 1
    headers={'Range' : 'bytes={}-{}'.format(offset, end)}
    
    chunk = io.BytesIO(key.get_contents_as_string(headers=headers))
    contents =  GzipFile(fileobj=chunk).read()
    file_warc = open(file_dest,mode='wb')
    file_warc.write(contents)
    file_warc.close()    

def update_html_index_file(url_key,url_target,folder_name):
    index_file_name = index_foldername + str(folder_name) + '/' +'html_index.txt'
    html_index_file = open(index_file_name,'a')
    outputstr = url_key +"," + url_target+"\n"
    html_index_file.write(outputstr)
    html_index_file.close()
    
public_dataset = get_aws_bucket()
fetch_and_write_html(public_dataset)

