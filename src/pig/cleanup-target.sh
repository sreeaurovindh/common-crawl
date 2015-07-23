cd ../java/warcbase
mvn clean package appassembler:assemble -DskipTests
rm -rf ../../pig/target/

cd ../../pig/
mkdir target
hadoop fs -rmr /finished/

cp /home/sree/dev/common-crawl/src/java/warcbase/target/warcbase-0.1.0-SNAPSHOT-fatjar.jar /home/sree/dev/common-crawl/src/pig/target/

