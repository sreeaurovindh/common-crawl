Deducing Structure of the Web
=============================

The goal of the project is to classify the structure of web pages and aggregate these structures based on frequency and domain information. This project would enable focused crawling of webpages and would help researchers to extract data at a web scale.  Due to enormity and unstructuredness of the dataset, we have planned to use tools from the Hadoop eco-system (Apache Pig with Tez, Apache Hive and HBase).

Specifically, Pages with similar html layouts  would have similar html components. Hence the document object model (DOM) path of the leaf nodes approximately describes the visual location of the component in page rendering. By specifically targeting on four major visual components such as buttons, image, link, and video , a collection of XPath expressions would be generated. Then grouping of these objects would be done based on path structure and domain information and then frequency of the web pages would be calculated.

Dataset 
-------

The dataset used for this project is from the May 2015 crawl of the web (Common Crawl) , which consists of over 159 TB of data with more than 2.05 billion webpages.

Credits
-------

Idea credits : http://research.microsoft.com/en-us/projects/website_structure_mining/ 

Web archive data Credits : Common Crawl http://commoncrawl.org/

IP - GeoLocation Databases : http://dev.maxmind.com/geoip/legacy/geolite/#IP_Geolocation 

Warc Extractor (Forked from here and followed with additions) : https://github.com/lintool/warcbase  

Disclaimer
---------

The views and opinions expressed in this article are those of the authors and do not necessarily reflect the official policy or work in progress of any company such as microsoft or common crawl. Examples of analysis performed within this article are only examples. They should not be utilized in real-world analytic products as they are based only on very limited and dated open source information.

This product includes GeoLite data created by MaxMind, available from 
<a href="http://www.maxmind.com">http://www.maxmind.com</a>.
