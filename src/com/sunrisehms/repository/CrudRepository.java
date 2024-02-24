
package com.sunrisehms.repository;

import java.util.List;
import org.hibernate.Session;

public interface CrudRepository<Entity, ID> extends Repository{
    ID save(Session session, Entity entity) throws Exception;
    void update(Session session, Entity entity) throws Exception;
    void delete(Session session, Entity entity) throws Exception;
    Entity get(Session session, ID id) throws Exception;
    List<Entity> getAll(Session session) throws Exception;
}
