# Execute an InfluxQL command and quit with -execute, and change the timestamp precision:
# influx -execute 'SELECT * FROM "jmeter.samples" LIMIT 3' -database=jmeter -precision=rfc3339;


influx -execute 'create database jmeter';
influx -execute 'create database graphana';

# no way to get the sampler names in influxdb with backend listnener as of now, so manually creating the entries with the following
# TODO replace with a loop statement

influx -execute 'INSERT jmeter.samples,RequestType=grpcRequest,RequestName=DebugSampler value=1' -database=jmeter -precision=rfc3339;
influx -execute 'INSERT jmeter.samples,RequestType=grpcRequest,RequestName=Greeter-Health-Check value=1' -database=jmeter -precision=rfc3339;
