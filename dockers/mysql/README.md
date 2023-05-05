# Build the Image

- docker build -t wsarachai/mysql-itsci:latest .
- docker build -t wsarachai/mysql-itsci:latest --build-arg=3306 .

# Create the container

- docker run --name itsci-mysql -v /Users/watcharinsarachai/Documents/workspace/docker/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=1234 -d --rm -p 3306:3306 wsarachai/mysql-itsci:latest

- docker run --name itsci-mysql --network itsci-net -v /Users/watcharinsarachai/Documents/workspace/docker/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=1234 -d --rm wsarachai/mysql-itsci:latest

- docker run --name itsci-mysql --network itsci-net -v /Users/watcharinsarachai/Documents/workspace/docker/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=1234 -d --rm  -p 3306:3306 wsarachai/mysql-itsci:latest
