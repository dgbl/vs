/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp3;
import java.util.ArrayList;


/**
 *
 * @author DNS
 */
public class WareList {
    
    private ArrayList<Ware> wList = new ArrayList<>();

    public WareList(){
        
    }
    
    public void addWare(Ware w){
        wList.add(w);
    }
    
    public void deleteWare(int i){
        wList.remove(i);
    }
    
    public Ware getWare(int i){
        return wList.get(i);
    }
    
    public int sizeOfWare(){
        return wList.size();
    }        
    
}
