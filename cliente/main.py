import client as taquin

def main():
    matrix = taquin.generate_matrix( 5 )
    domain = raw_input( "Ingrese el nombre de dominio: " )
    pid = raw_input("Ingrese el id del jugador: ")
    pid = int( pid )
    oponnent = raw_input( "Ingrese el id del oponente: ")
    oponnent = int( oponnent )
    name = raw_input( "Ingrese el nombre del jugador: " )
    taquin.create_player( domain , pid , name )
    taquin.challenge( domain , matrix , 1 , 1 , oponnent )
    while True:
        direccion = raw_input( "Ingrese la direccion r, l, u, d, magic ")
        if direccion == "r":
            taquin.move_right( domain , pid )
        elif direccion == "l":
            taquin.move_left( domain , pid )
        elif direccion == "u":
            taquin.move_up( domain , pid )
        elif direccion == "d":
            taquin.move_down( domain , pid )
        elif direccion == "magic":
            taquin.check( domain , pid )
        print "Tablero actual:"
        print taquin.get_board( domain, pid )


if __name__ == '__main__':
    main()
