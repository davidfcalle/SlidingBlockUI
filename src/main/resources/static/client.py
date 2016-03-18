import json
import requests

domain = "localhost:8080/"

"""
esta funcion recibe la matriz y la posicion x , y donde esta la posicion en blanco
"""
def challenge( matrix , x , y , opponentId ):
    body = {
        "currentState" : matrix,
        "blank" : { "row" : x , "column" : y }
    }
    response = requests.post( domain + "api/player/%i/board/" % ( opponentId ) , data = json.dumps( body ) )
"""
esta funcion recibe  el pid del jugador actual y mueve la ficha en blanco a la izquerda
"""
def move_left( pId ):
    response = requests.post( domain + "api/player/%i/move/left/" % ( pId ) )
"""
esta funcion recibe  el pid del jugador actual y mueve la ficha en blanco a la derecha
"""
def move_right( pId ):
    response = requests.post( domain + "api/player/%i/move/right/" % ( pId ) )
"""
esta funcion recibe  el pid del jugador actual y mueve la ficha en blanco a la arriba
"""
def move_up( pId ):
    response = requests.post( domain + "api/player/%i/move/up/" % ( pId ) )
"""
esta funcion recibe  el pid del jugador actual y mueve la ficha en blanco hacia abajo
"""
def move_down( pId ):
    response = requests.post( domain + "api/player/%i/move/down/" % ( pId ) )

def create_player( pId  , name ):
    response = requests.post( domain + "api/player/%i/new/%s/" % ( pId , name ) )

def main():
    matrix = [ [ 1 , 5 ] , [ 2 , 4 ] ]
    challenge( matrix , 1 , 1 , 2 )

if __name__ == '__main__':
    main()
