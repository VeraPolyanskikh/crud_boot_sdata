package crud.boot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Service
@Transactional
public class BaseService {

    @PersistenceContext
    protected EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

}
