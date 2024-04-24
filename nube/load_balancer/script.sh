#!/bin/bash

#Colocar el Host aqui Example: ec2-54-165-241-91.compute-1.amazonaws.com
host1=ec2-44-211-227-84.compute-1.amazonaws.com
host2=ec2-54-87-246-20.compute-1.amazonaws.com
host3=ec2-35-171-8-42.compute-1.amazonaws.com

host_balanceo=ec2-54-165-162-227.compute-1.amazonaws.com

ruta_key=~/Downloads/key2.pem
chmod 400 $ruta_key

scp -i $ruta_key files/* .env docker-compose.yml Dockerfile ubuntu@$host1:~/
scp -i $ruta_key files/* .env docker-compose.yml Dockerfile ubuntu@$host2:~/
scp -i $ruta_key files/* .env docker-compose.yml Dockerfile ubuntu@$host3:~/
scp -i $ruta_key files/* .env docker-compose.yml Dockerfile ubuntu@$host_balanceo:~/


## Leer hosts desde un archivo
#hosts_file="hosts.txt"
#hosts=$(cat $hosts_file)
#
## Obtener parámetros
#ruta_key="~/Downloads/key1.pem"
#
#chmod 400 $ruta_key
#
## Bucle para ejecutar SCP en cada host
#for host in $hosts; do
#  echo "Ejecutando SCP en $host..."
#  comando_scp="scp -i $ruta_key script_remote.sh ../.env ../docker-compose.yml ../Dockerfile  ubuntu@$host:~/"
#  $comando_scp
#  echo "SCP completado en $host."
#  ssh -i $ruta_key ubuntu@$host "sudo bash script_remote.sh"
#
#done
