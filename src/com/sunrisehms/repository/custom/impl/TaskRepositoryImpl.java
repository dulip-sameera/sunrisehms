
package com.sunrisehms.repository.custom.impl;

import com.sunrisehms.entity.TaskEntity;
import com.sunrisehms.repository.TaskRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class TaskRepositoryImpl implements TaskRepository{

    @Override
    public TaskEntity get(Session session, Integer Id) throws Exception {
        return session.get(TaskEntity.class, Id);
    }

    @Override
    public List<TaskEntity> getAll(Session session) throws Exception {
        String hql = "FROM TaskEntity";
        Query query = session.createQuery(hql);
        return query.list();
    }
    
}
