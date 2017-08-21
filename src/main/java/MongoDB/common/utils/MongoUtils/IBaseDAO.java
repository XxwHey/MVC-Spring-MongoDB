package MongoDB.common.utils.MongoUtils;

import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiexw on 2017/8/10.
 */
public interface IBaseDAO<T, ID extends Serializable> {

    public T get(ID id);

    public T save(T entity);

    public List<T> listAll();

    public List<T> listByQuery(Query query);

    public T listUniqueByQuery(Query query);

}
