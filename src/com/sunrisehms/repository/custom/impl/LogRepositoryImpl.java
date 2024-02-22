
package com.sunrisehms.repository.custom.impl;

import com.sunrisehms.entity.LogEntity;
import com.sunrisehms.repository.custom.LogRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class LogRepositoryImpl implements LogRepository{

    @Override
    public Long save(Session session, LogEntity log) throws Exception {
        return (Long)session.save(log);
    }

    @Override
    public void update(Session session, LogEntity log) throws Exception {
        session.update(log);
    }

    @Override
    public void delete(Session session, LogEntity log) throws Exception {
        session.delete(log);
    }

    @Override
    public LogEntity get(Session session, Long id) throws Exception {
        return session.get(LogEntity.class, id);
    }

    @Override
    public List<LogEntity> getAll(Session session) throws Exception {
        String hql = "FROM LogEntity";
        Query query = session.createQuery(hql);
        return query.list();
    }
    
}
