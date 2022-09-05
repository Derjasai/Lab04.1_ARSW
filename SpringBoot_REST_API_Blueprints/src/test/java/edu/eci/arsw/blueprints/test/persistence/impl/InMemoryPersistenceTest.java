/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }
                
        
    }

    @Test
    public void shouldGetBlueprint(){
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        try{
            Point[] pts0=new Point[]{new Point(25, 15),new Point(15, 15)};
            Blueprint bp0=new Blueprint("Zoro", "edificio",pts0);
            ibpp.saveBlueprint(bp0);
            Blueprint bp = ibpp.getBlueprint("Zoro", "edificio");
            assertEquals(bp.getAuthor(), "Zoro");
            assertEquals(bp.getName(), "edificio");
        }catch (BlueprintNotFoundException e) {
            fail("No se encuentra autor");
        } catch (BlueprintPersistenceException e) {
            fail("Error al agregar");
        }
    }

    @Test
    public void shouldGetBpByAuthor(){
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        Point[] pts0=new Point[]{new Point(25, 15),new Point(15, 15)};
        Blueprint bp0=new Blueprint("Luffy", "mypaint1",pts0);
        Point[] pts1=new Point[]{new Point(46, 46),new Point(16, 16)};
        Blueprint bp1=new Blueprint("Luffy", "mypaint2",pts1);
        Point[] pts2=new Point[]{new Point(10, 15),new Point(20, 25)};
        Blueprint bp2=new Blueprint("Bob", "mypaint2",pts2);
        try{
            ibpp.saveBlueprint(bp0);
            ibpp.saveBlueprint(bp1);
            ibpp.saveBlueprint(bp2);
            assertEquals(ibpp.getBlueprintsByAuthor("Luffy").size(), 2);
        } catch (BlueprintPersistenceException | BlueprintNotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldNotGetBpByAuthor(){
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        Point[] pts0=new Point[]{new Point(25, 15),new Point(15, 15)};
        Blueprint bp0=new Blueprint("Luffy", "mypaint1",pts0);
        Point[] pts1=new Point[]{new Point(46, 46),new Point(16, 16)};
        Blueprint bp1=new Blueprint("Luffy", "mypaint2",pts1);
        Point[] pts2=new Point[]{new Point(10, 15),new Point(20, 25)};
        Blueprint bp2=new Blueprint("Bob", "mypaint2",pts2);
        try{
            ibpp.saveBlueprint(bp0);
            ibpp.saveBlueprint(bp1);
            ibpp.saveBlueprint(bp2);
            assertNotEquals(ibpp.getBlueprintsByAuthor("Lufy").size(), 2);
        } catch (BlueprintPersistenceException | BlueprintNotFoundException e) {
            fail(e.getMessage());
        }
    }

    
}
