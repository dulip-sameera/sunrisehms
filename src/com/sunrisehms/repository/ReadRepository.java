
package com.sunrisehms.repository;

import java.util.List;
import org.hibernate.Session;

public interface ReadRepository<Entity, T> extends Repository{
    Entity get(Session session, T Id) throws Exception;
    List<Entity> getAll(Session session) throws Exception;
}
