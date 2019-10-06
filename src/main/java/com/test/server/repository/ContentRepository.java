package com.test.server.repository;



import com.test.server.entity.Content;
import com.test.server.entity.User;
import javafx.scene.Camera;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Repository
@Transactional
public class ContentRepository {


    @PersistenceContext
    private EntityManager em;


    public Page<Content> selectContent(int pageNum, int pageSize) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Content> cq = cb.createQuery(Content.class);
        Root<Content> root = cq.from(Content.class);

        Order orders = cb.asc(root.get("id"));
        cq.select(root).orderBy(orders);

        List<Content> resultList = em.createQuery(cq).setFirstResult(pageNum * pageSize).setMaxResults(pageSize).getResultList();
        Long totalRows = countContent();

        return new PageImpl<>(resultList, PageRequest.of(pageNum, pageSize), totalRows);
    }

    public Long countContent() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Content> root = cq.from(Content.class);
        cq.select(cb.count(root));
        return em.createQuery(cq).getSingleResult();
    }

}
