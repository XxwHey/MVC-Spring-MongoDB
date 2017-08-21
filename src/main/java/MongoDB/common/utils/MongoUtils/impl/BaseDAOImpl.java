package MongoDB.common.utils.MongoUtils.impl;

import MongoDB.common.utils.MongoUtils.IBaseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by xiexw on 2017/8/10.
 */
@Repository
public class BaseDAOImpl<T, ID extends Serializable> implements IBaseDAO<T, ID> {

    @Autowired
    private MongoTemplate mongoTemplate;

    private Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T get(ID id) {
        Query query = new Query();
        query.addCriteria(new Criteria("_id").is(id));
        return this.mongoTemplate.findOne(query, getTClass());
    }

    @Override
    public T save(T entity) {
        this.mongoTemplate.insert(entity);
        return entity;
    }

    @Override
    public List<T> listAll() {
        return this.mongoTemplate.findAll(getTClass());
    }

    @Override
    public List<T> listByQuery(Query query) {
        return this.mongoTemplate.find(query, getTClass());
    }

    @Override
    public T listUniqueByQuery(Query query) {
        return this.mongoTemplate.findOne(query, getTClass());
    }
}
