# InfluxDB installation and set up

# Install influxdb
 cat <<EOF | sudo tee /etc/yum.repos.d/influxdb.repo
[influxdb]
name = InfluxDB Repository - RHEL \$releasever
baseurl = https://repos.influxdata.com/rhel/\$releasever/\$basearch/stable
enabled = 1
gpgcheck = 1
gpgkey = https://repos.influxdata.com/influxdb.key
EOF

yum install -y influxdb

# start the service
sudo systemctl start influxdb
 
# enable influxdb to start on boot
systemctl enable influxdb.service


#-----------------------------------------------------------------------------

# Grafana installation and set up

# Install grafana
yum install -y https://s3-us-west-2.amazonaws.com/grafana-releases/release/grafana-4.2.0-1.x86_64.rpm

# start grafana
systemctl start grafana-server
 
# enable grafana to start on boot
/sbin/chkconfig --add grafana-server
systemctl enable grafana-server.service

# to run grafana on port 80
sudo iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 3000
 
