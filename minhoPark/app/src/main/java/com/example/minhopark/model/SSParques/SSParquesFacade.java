package com.example.minhopark.model.SSParques;

import com.example.minhopark.DataBase.CategoriaDAO;
import com.example.minhopark.DataBase.HorarioDAO;
import com.example.minhopark.DataBase.ParqueDAO;
import com.example.minhopark.model.SSUtilizadores.IUtilizadores;
import com.example.minhopark.model.SSUtilizadores.Preferencia;
import com.example.minhopark.model.SSUtilizadores.SSUtilizadorFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SSParquesFacade implements IParques, Serializable {


    CategoriaDAO categorias;
    HorarioDAO horarios;
    ParqueDAO parques;
    IUtilizadores utilizador;

    
    public SSParquesFacade(Preferencia p, Set<Integer> favoritos){
        this.categorias = new CategoriaDAO();
        this.horarios = new HorarioDAO();
        this.parques = new ParqueDAO();
        this.utilizador = new SSUtilizadorFacade(p,favoritos);

    }


    @Override
    public Parque getParque(int id) {
        return parques.getParque(id);
    }

    @Override
    public void addParque(Parque p){
        parques.addParque(p);
    }

    public static double distance(double lat1, double lat2, double lng1, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = (earthRadius * c);

        return dist;
    }



    @Override
    public List<Parque> pesquisa(Preferencia p) {

        Set<Parque> parques = new TreeSet<>( (p1,p2) -> {

            String[] coordenadasInicio = this.utilizador.getCoordenadas().split(",");

            String[] coordenadasP1 = p1.getCoordenadas().split(",");
            String[] coordenadasP2 = p2.getCoordenadas().split(",");

            double d1 = distance(Double.parseDouble(coordenadasP1[0]),Double.parseDouble(coordenadasInicio[0]),Double.parseDouble(coordenadasP1[1]),Double.parseDouble(coordenadasInicio[1]));
            double d2 = distance(Double.parseDouble(coordenadasP2[0]),Double.parseDouble(coordenadasInicio[0]),Double.parseDouble(coordenadasP2[1]),Double.parseDouble(coordenadasInicio[1]));


            return Double.compare(d1,d2);

        });

        List<Parque> res = new ArrayList<>();

        for(String id : p.getTiposParques()){
            for(Parque parque : this.parques.getParquesFiltered(id)){
                parques.add(parque);
            }
        }


        int i = 0;
        for(Parque pa : parques){
            res.add(pa);
            i++;
            if(i >= p.getnParques()) return res;
        }

        return res;
    }



    public Preferencia getPreferencia(){
        return  this.utilizador.getPreferencia();
    }

    public List<Parque> getParquesFavoritos (Set<Integer> favoritos) {
        List<Parque> parquesFavoritos = new ArrayList<>();
        for (Integer id : favoritos) {
            parquesFavoritos.add(parques.getParque(id));
        }
        return parquesFavoritos;
    }

    public static void main(String args[]){


        Preferencia p = new Preferencia();

        SSParquesFacade ssParquesFacade = new SSParquesFacade(p,null);

        System.out.println(ssParquesFacade.pesquisa(p));



    }



}
