package com.sunrisehms.repository.custom.impl;

import com.sunrisehms.entity.CustomerStatus;
import com.sunrisehms.repository.custom.CustomerStatusRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class CustomerStatusRepositoryImpl implements CustomerStatusRepository{

    @Override
    public CustomerStatus get(Session session, Integer Id) throws Exception {
        return session.get(CustomerStatus.class, Id);
    }

    @Override
    public List<CustomerStatus> getAll(Session session) throws Exception {
        String hql = "FROM CustomerStatus";
        Query query = session.createQuery(hql);
        return query.list();
    }
    
}
