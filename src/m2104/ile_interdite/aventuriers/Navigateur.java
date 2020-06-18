/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.aventuriers;

import m2104.ile_interdite.modele.IleInterdite;
import m2104.ile_interdite.util.Utils.Pion;

/**
*
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
* 
*/
public class Navigateur extends Aventurier{
    public Navigateur(IleInterdite ileInterdite) {
        super(ileInterdite, Pion.JAUNE);
    }
}
