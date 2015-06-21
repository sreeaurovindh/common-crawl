import json
import urllib3

# This code is used to fetch the total number of pages in URL common crawl index
# and to obtain the URLS and its offsets

#Decenber 2014 TO April 2015

indexed_paths = ['CC-MAIN-2014-52','CC-MAIN-2015-06','CC-MAIN-2015-11','CC-MAIN-2015-14','CC-MAIN-2015-18']
actual_s3_url = 'https://aws-publicdatasets.s3.amazonaws.com/'
index_common_crawl = 'http://index.commoncrawl.org/'
out_folder_name ='indeed-2015-11'
out_folder_path = '/home/sree/Projects/common-crawl/data'
web_page_name = '*.indeed.com'
output_numpage_request = '&output=json&showNumPages=true'
output_content ='&output=json&page='


#Constructed URL Paths
num_page_request = index_common_crawl + indexed_paths[2] + '-index?url=' +web_page_name + output_numpage_request
output_page_url = index_common_crawl + indexed_paths[2]  + '-index?url=' +web_page_name + output_content
output_folder_path = out_folder_path+'/'+ out_folder_name +"/"

# Fetch Total number of pages in that index of common crawl

#Create Pool Manager
http = urllib3.PoolManager()
response = http.request('GET',num_page_request)
output = response.data.decode("utf-8")
print(output)


numPg_json = json.loads(output)
total_pages = int(numPg_json['pages'])

for i in range(1,total_pages):
    call_url = output_page_url+ str(i)
    response = http.request('GET',call_url)
    utf_response = response.data.decode("utf-8")
    file_json_name = output_folder_path + str(i) + ".json"
    file_json = open(file_json_name,mode='w')
    file_json.write(utf_response)
    file_json.close()
    