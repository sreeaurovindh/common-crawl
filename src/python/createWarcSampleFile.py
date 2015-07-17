count = 0
content = ""
fw = open('sample.warc','w', encoding="ISO-8859-1")
with open('CC-MAIN-20150521113204-00004-ip-10-180-206-219.ec2.internal.warc', encoding="ISO-8859-1") as fr:
	for line in fr:
		fw.write(line)
		count =count+1
		if count>1000000:
			fw.close()
			break