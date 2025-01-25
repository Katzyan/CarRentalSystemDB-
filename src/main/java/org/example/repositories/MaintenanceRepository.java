package org.example.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Maintenance;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class MaintenanceRepository {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


    public void addMaintenanceRepository(Maintenance maintenance)  {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        if(!maintenance.getMaintenanceDetailsList().isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();
            try{
                String jsonMaintenanceDetails = objectMapper.writeValueAsString(maintenance.getMaintenanceDetailsList());
                maintenance.setMaintenanceDetails(jsonMaintenanceDetails);
            } catch (Exception ignored){
            }
        }
        session.save(maintenance);

        session.getTransaction().commit();
        session.close();
    }

    public Maintenance getMaintenanceByID(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Maintenance maintenance = session.get(Maintenance.class, id);
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            maintenance.setMaintenanceDetailsList(objectMapper.readValue(maintenance.getMaintenanceDetails(), new TypeReference<List<String>>() {
            }));

        }catch (Exception ignored){

        }
        session.getTransaction().commit();
        session.close();
        return maintenance;


    }

}
