import json
import requests

def set_name( domain  , pid , name ):
    r = requests.post( domain + "/api/player/%i/new/%s/" % ( pid , name ))
"""
esta funcion recibe la matriz y la posicion x , y donde esta la posicion en blanco
"""
def challenge( domain , matrix , x , y , opponentId ):
    body = {
        "currentState" : matrix,
        "blank" : { "row" : x , "column" : y },
        "movements" : 0
    }
    print domain + "/api/player/%i/challenge/" % ( opponentId )
    response = requests.post( domain + "/api/player/%i/challenge/" % ( opponentId ) , json =  body  )
    print response
"""
    funcion que retorna la matriz del reto que hizo un jugador
"""
def get_challenge( domain , pId ):
    r = requests.get( domain + "/api/board/%s/" % ( pId ))

"""
esta funcion recibe  el pid del jugador actual y mueve la ficha en blanco a la izquerda
"""
def move_left( domain , pId ):
    response = requests.post( domain + "/api/player/%i/board/move/left/" % ( pId ) )
"""
esta funcion recibe  el pid del jugador actual y mueve la ficha en blanco a la derecha
"""
def move_right( domain , pId ):
    print domain + "/api/player/%i/move/right/" % ( pId )
    response = requests.post( domain + "/api/player/%i/board/move/right/" % ( pId ) )

"""
esta funcion recibe  el pid del jugador actual y mueve la ficha en blanco a la arriba
"""
def move_up( domain , pId ):
    response = requests.post( domain + "/api/player/%i/board/move/up/" % ( pId ) )
"""
esta funcion recibe  el pid del jugador actual y mueve la ficha en blanco hacia abajo
"""
def move_down( domain , pId ):
    response = requests.post( domain + "/api/player/%i/board/move/down/" % ( pId ) )

def create_player(  domain , pId  , name ):
    response = requests.post( domain + "/api/player/%i/new/%s/" % ( pId , name ) )


def check( domain , pid):
    move_right( domain , pid )
    move_right( domain , pid )
    move_right( domain , pid )
    move_right( domain , pid )
    move_right( domain , pid )
    move_right( domain , pid )

    move_left( domain , pid )
    move_left( domain , pid )
    move_left( domain , pid )
    move_left( domain , pid )
    move_left( domain , pid )
    move_left( domain , pid )

    move_down( domain , pid )
    move_down( domain , pid )
    move_down( domain , pid )
    move_down( domain , pid )
    move_down( domain , pid )
    move_down( domain , pid )

    move_up( domain , pid )
    move_up( domain , pid )
    move_up( domain , pid )
    move_up( domain , pid )
    move_up( domain , pid )
    move_up( domain , pid ) 


def main():
    matrix = [ [ 1 , 5, 3 ] , [ 2 , 4, 9 ], [ 2 , 4 , 7 ] ]
    domain = raw_input( "Ingrese el nombre de dominio: " )
    pid = raw_input("Ingrese el id del jugador: ")
    pid = int( pid )
    oponnent = raw_input( "Ingrese el id del oponente: ")
    oponnent = int( oponnent )
    name = raw_input( "Ingrese el nombre del jugador: " )
    set_name( domain , pid , name )
    challenge( domain , matrix , 1 , 1 , oponnent )
    check( domain , pid )
    """ hace el challenge de la matriz
    while True:
        direccion = raw_input( "Ingrese la direccion R , L , U , D ")
        if direccion == "R":
            move_right( domain , pid )
        elif direccion == "L":
            move_left( domain , pid )
        elif direccion == "U":
            move_up( domain , pid )
        elif direccion == "D":
            move_down( domain , pid )
    """


if __name__ == '__main__':
    main()
