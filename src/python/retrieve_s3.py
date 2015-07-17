# Code modified from  https://github.com/trivio/common_crawl_index
import boto
from gzip import GzipFile
import io
from boto.s3.key import Key
 

#{"urlkey": "com,indeed)/jobs?l=ohio&q=desktop+support+specialist&start=30", "timestamp": "20141226155330", "status": "200",
#"url": "http://www.indeed.com/jobs?q=Desktop+Support+Specialist&l=Ohio&start=30", 
#"filename": "common-crawl/crawl-data/CC-MAIN-2014-52/segments/1419447549371.17/warc/CC-MAIN-20141224185909-00078-ip-10-231-17-201.ec2.internal.warc.gz", "length": "21377", "mime": "text/html", "offset": "262285888", "digest": "FA5CZXSX6ELIPPBBHENFKYTOGSOR6GBS"}
length = 21377
offset = 262285888
keyname = 'common-crawl/crawl-data/CC-MAIN-2014-52/segments/1419447549371.17/warc/CC-MAIN-20141224185909-00078-ip-10-231-17-201.ec2.internal.warc.gz'

out_file = '/home/sree/Projects/common-crawl/data/warcoutput/output.txt'

def get_aws_bucket():
    conn = boto.connect_s3(anon=True)
    public_dataset = conn.get_bucket('aws-publicdatasets')     
    return public_dataset


def get_warc_from_s3(bucket,keyname,offset,length,file_dest):
    key = bucket.lookup(keyname)
    end = offset + length - 1
    headers={'Range' : 'bytes={}-{}'.format(offset, end)}
    
    chunk = io.BytesIO(key.get_contents_as_string(headers=headers))
    contents =  GzipFile(fileobj=chunk).read()
    file_warc = open(file_dest,mode='wb')
    file_warc.write(contents)
    file_warc.close()    


public_dataset = get_aws_bucket()
get_warc_from_s3(public_dataset,keyname,offset,length,out_file)

