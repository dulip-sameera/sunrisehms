
package com.sunrisehms.repository.custom.impl;

import com.sunrisehms.entity.CustomerEntity;
import com.sunrisehms.repository.custom.CustomerRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class CustomerRepositoryImpl implements CustomerRepository{

    @Override
    public Long save(Session session, CustomerEntity entity) throws Exception {
        return (Long)session.save(entity);
    }

    @Override
    public void update(Session session, CustomerEntity entity) throws Exception {
        session.update(entity);
    }

    @Override
    public void delete(Session session, CustomerEntity entity) throws Exception {
        session.delete(entity);
    }

    @Override
    public CustomerEntity get(Session session, Long id) throws Exception {
        return session.get(CustomerEntity.class, id);
    }

    @Override
    public List<CustomerEntity> getAll(Session session) throws Exception {
        String hql = "FROM CustomerEntity";
        Query query = session.createQuery(hql);
        return query.list();
    }

    @Override
    public CustomerEntity getByNIC(Session session, String nic) throws Exception {
        String hql = "FROM CustomerEntity c WHERE c.nic=:nic";
        Query query = session.createQuery(hql);
        query.setParameter("nic", nic);
        
        List<CustomerEntity> customers = query.list();
        
        if (customers.isEmpty()) {
            return null;
        } else {
            return customers.get(0);
        }
    }
    
}
