## Synopsis

Grafana and influxdb integration with jmeter to allow live monitoring of long running tests that run in non gui mode

## InfluxDB and Grafana Installation
run 'influxdb-grafana-installation.sh' as root to install InfluxDB and Grafana

## InfluxDB config and set up

### InfluxDB config
Edit the graphite and admin sections in /etc/influxdb/influxdb.conf file as follows

```
[graphite]]
  enabled = true
  database = "jmeter"
  bind-address = ":2003"
 
[admin]
  # Determines whether the admin service is enabled.
  enabled = true
```
 
### Restart the service
```
sudo systemctl stop influxdb
sudo systemctl start influxdb
```


### Run InfluxQL
After Influxdb is installed and is running 
run the 'influxQL-commands.sh' as root on the Influxdb server

## Grafana dashboard set up
After grafana server is installed and is running 
login to the portal and create a database entry for influxdb 
```
Name: influxdb
Type: InfluxDB
url: http://localhost:8086
Database: jmeter
```
import the 'Whatalokation_JMeter_Stress_Test_Dashboard.json' to create the dashboard

http://<grafana-server-host>/dashboard/new/?editview=import

set refresh interval to 5s

## Live Monitoring
REMEMBER TO ENABLE BACKEND LISTENER IN THE TEST!

When the jmeter test is started, the graphs and other panels shall start to populate