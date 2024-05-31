package encapsulaciones;

import java.util.ArrayList;

public class CarritoCompra {
    private static long id = 1;
    private ArrayList<Producto> listaProducto = new ArrayList<>();
    public CarritoCompra(ArrayList<Producto> listaProducto) {
        this.id += 1;
        this.listaProducto = listaProducto;
    }
    public CarritoCompra() {
    }
    public ArrayList<Producto> getListaProducto() {
        return listaProducto;
    }
    public void setListaProducto(ArrayList<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public float getTotalCarrito(){
        float suma = 0;
        for(Producto producto: listaProducto){
            suma += producto.getPrecio();
        }
        return suma;
    }
}
