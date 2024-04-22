from zeep import Client
import json

cliente = Client(wsdl='http://localhost:8888/webServ/UrlWebServices?wsdl')

# urlByUser = cliente.service.getListaUrlByUsuario(3)
# jsonUrlUser = json.loads(urlByUser)
# json_bonito = json.dumps(jsonUrlUser, indent=4)
# print(json_bonito)

url = cliente.service.createUrlToUser(2, "https://www.google.com")
jsonResponse = json.loads(url)
json_bonito2 = json.dumps(jsonResponse, indent=4)
print(json_bonito2)