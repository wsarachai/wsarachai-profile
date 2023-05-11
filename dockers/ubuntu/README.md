# Ubuntu 22.04
- [Image link](https://hub.docker.com/_/ubuntu)

## Build this image
   ```
   docker build -t wsarachai/ubuntu-itsci:latest .
   ```
# push
    ```
    docker push wsarachai/ubuntu-itsci:latest
    ```
## # Create the container
    ```
    docker run --name ubuntu-itsci -it --rm wsarachai/ubuntu-itsci:latest
    ```
## Locales in Dockerfile
Given that it is a minimal install of Ubuntu, this image only includes the C, C.UTF-8, and POSIX locales by default. For most uses requiring a UTF-8 locale, C.UTF-8 is likely sufficient (-e LANG=C.UTF-8 or ENV LANG C.UTF-8).

For uses where that is not sufficient, other locales can be installed/generated via the locales package. [PostgreSQL has a good example of doing so](https://github.com/docker-library/postgres/blob/69bc540ecfffecce72d49fa7e4a46680350037f9/9.6/Dockerfile#L21-L24), copied below:
```
RUN apt-get update && apt-get install -y locales && rm -rf /var/lib/apt/lists/* && localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8
ENV LANG en_US.utf8
```

## You can SSH in to docker container as root by using
   ```
   docker exec -it --user root <container_id> /bin/bash
   ```
   - Then change root password using this
     ```
     passwd root
     ```
   - Make sure sudo is installed check by entering
     ```
     sudo
     ```
   - if it is not installed install it
     ```
     apt-get install -y sudo
     ```
## If you want to give sudo permissions for user dev you can add user dev to sudo group
   ```
   useradd -m wsarachai
   usermod -aG sudo wsarachai
   passwd wsarachai
   ```
   <br/>Now you'll be able to run sudo level commands from your dev user while inside the container or else you can switch to root inside the container by using the password you set earlier.
   <br/>To test it login as user dev and list the contents of root directory which is normally only accessible to the root user.
   ```
   sudo ls -la /root
   ```
   <br/>Enter password for dev
   <br/>If your user is in the proper group and you entered the password correctly, the command that you issued with sudo should run with root privileges.

## Install new packages
   ```
   apt-get update
   apt-get -y install curl
   apt-get -y install tree
   ...
   ```
   Or
   ```
   apt-get update && apt-get install -y package-bar
   ```
