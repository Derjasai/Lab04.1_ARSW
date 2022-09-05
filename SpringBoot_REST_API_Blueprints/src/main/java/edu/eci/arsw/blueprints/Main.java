package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String a[]) throws BlueprintNotFoundException, BlueprintPersistenceException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bpServices = ac.getBean(BlueprintsServices.class);

        Point[] pts0 = new Point[]{new Point(25, 15),new Point(15, 15)};
        Blueprint bp0 = new Blueprint("Zoro", "edificio",pts0);

        Blueprint bp1 = new Blueprint("Luffy","Meat!!");

        //Registrar planos especificos
        bpServices.addNewBlueprint(bp0);
        //Registrar planos
        bpServices.addNewBlueprint(bp1);

        for(Blueprint blueprint:bpServices.getAllBlueprints()){
            //Consultar planos
            System.out.println(blueprint.getName());
            System.out.println(blueprint.getAuthor());
        }
    }
}
