import boto, datetime, time
from datetime import date, timedelta
import statistics
import numpy as np
import time as tm
 
# Enter your AWS credentials
aws_key = "AKIAI4BVSKH5LVTR5VTA"
aws_secret = "cuKn897WgN4aIWsdUff6Nk1GhOcr1EHO2BBiJSsM"

# Details of instance & time range you want to find spot prices for
instanceType = 'c3.large'

#Today
current_date =datetime.datetime.now().isoformat()
# Five Days before
sixdays =datetime.datetime.now() - timedelta(days=6)
startTime = sixdays.isoformat()

threeday = datetime.datetime.now() - timedelta(days=3)
threeday = threeday.isoformat()

oneday = datetime.datetime.now() - timedelta(days=3)
 

endTime = current_date
#startTime = '2015-06-05T20:26:07.727470'
#endTime = '2015-07-05T20:26:07.727470'
aZ = 'us-west'

# Some other variables
maxCost = 0.0 
minCost = 0.0
minTime = float("inf")
maxTime = 0.0
totalPrice = 0.0
oldTimee = 0.0

# Connect to EC2
conn = boto.connect_ec2(aws_key, aws_secret)

# Get prices for instance, AZ and time range
prices = conn.get_spot_price_history(instance_type=instanceType, 
  start_time=startTime, end_time=endTime, availability_zone=aZ)

# Output the prices
print("Historic prices")
all_prices = []
for price in prices:
  timee = time.mktime(datetime.datetime.strptime(price.timestamp, 
    "%Y-%m-%dT%H:%M:%S.000Z" ).timetuple())
  print("\t" + price.timestamp + " => " + str(price.price))
  all_prices.append(price.price)
  #time = tm.strptime(price.timestamp,"%Y-%m-%dT%H:%M:%S.000Z")
  
  # Get max and min time from results
  if timee < minTime:
    minTime = timee
  if timee > maxTime:
    maxTime = timee
  # Get the max cost
  if price.price > maxCost:
    maxCost = price.price
  # Calculate total price
  if not (oldTimee == 0):
    totalPrice += (price.price * abs(timee - oldTimee)) / 3600
  oldTimee = timee

# Difference b/w first and last returned times
timeDiff = maxTime - minTime

a = np.array(all_prices)
p = np.percentile(a, 75)
p_90 = np.percentile(a,80)
median_sixdays = statistics.median(all_prices)
# Output aggregate, average and max results
print("For: one %s in %s" % (instanceType, aZ))
print("From: %s to %s" % (startTime, endTime))
print("Total cost = $" + str(totalPrice))
print('========================================')
print('Current price of %s = $ %s  ' %(instanceType,all_prices[0]))
print('=========================================')
print("---------------------------------------------------------")
print("Avg hourly cost sixdays = $ %s" %statistics.mean(all_prices))
print("Max hourly Cost six days= $" + str(max(all_prices)))
print("Min hourly Cost six days = $" + str(min(all_prices)))
print("Median hourly Cost six days= $" + str(median_sixdays))
print("75 and 90 percentile cost six days = $" + str(p) +"--$ =" +str(p_90))
print("Last 10 Price quotes greater than 75 Percentile - Median - 90 percentile :: " + str(all(i < p for i in all_prices[1:10])) + "-"+ str(all(i < median_sixdays for i in all_prices[1:10])) +"--"+str(all(i < p_90 for i in all_prices[1:10]))) 
