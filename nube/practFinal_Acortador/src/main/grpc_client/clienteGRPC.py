import grpc
import json
from UrlRn_pb2 import UrlByUsuarioRequest, crearUrlRequest
from UrlRn_pb2_grpc import UrlRnStub

channel = grpc.insecure_channel('localhost:50051')
stub = UrlRnStub(channel)

request = UrlByUsuarioRequest(usuarioID=3)
response = stub.getUrlByUsuario(request)

longUrl = 'https://probandoRedireccion.com'
request = crearUrlRequest(usuarioID=3, longUrl=longUrl)
response = stub.crearUrlByUsuario(request)

# print('Código de respuesta:', response.codRespuesta)
# jsonUrlUser = json.loads(response.jsonUrl)
# json_bonito = json.dumps(jsonUrlUser, indent=4)
# print(json_bonito)

