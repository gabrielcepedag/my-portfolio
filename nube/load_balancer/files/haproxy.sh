#!/usr/bin/env bash
echo "Configuracion sesiones centralizadas de HAProxy"

# Creando una copia del archivo actual.
if [ ! -e "/etc/haproxy/haproxy.cfg.original" ]; then
  echo "Creando archivo de backup"
  sudo cp /etc/haproxy/haproxy.cfg /etc/haproxy/haproxy.cfg.original
fi

sudo apt update && sudo apt install -y haproxy certbot nmap

# Copiando el archivo simple.
sudo cp ~/haproxy.cfg.sesiones-centralizadas /etc/haproxy/haproxy.cfg

# Reinicando el servicio de HAProxy
sudo service haproxy stop && sudo service haproxy start


#sudo mkdir -p /etc/haproxy/certs
#sudo -E bash -c 'sudo cat /etc/letsencrypt/live/practica-6.cloudfoliohub.com/fullchain.pem /etc/letsencrypt/live/practica-6.cloudfoliohub.com/privkey.pem > /etc/haproxy/certs/$DOMAIN.pem'
#sudo chmod -R go-rwx /etc/haproxy/certs
#https://ceyhunozgun.blogspot.com/2017/04/storing-user-sessions-in-redis-using-elasticache.html
